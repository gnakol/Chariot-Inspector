package fr_scapartois_auto.chariot_inspector.battery_usage.service;

import fr_scapartois_auto.chariot_inspector.battery.beans.Battery;
import fr_scapartois_auto.chariot_inspector.battery.dtos.BatteryDTO;
import fr_scapartois_auto.chariot_inspector.battery.mappers.BatteryMapper;
import fr_scapartois_auto.chariot_inspector.battery.mappers.BatteryMapperImpl;
import fr_scapartois_auto.chariot_inspector.battery.repositories.BatteryRepository;
import fr_scapartois_auto.chariot_inspector.battery_usage.bean.BatteryUsage;
import fr_scapartois_auto.chariot_inspector.battery_usage.mapper.BatteryUsageMapper;
import fr_scapartois_auto.chariot_inspector.battery_usage.dto.BatteryUsageDTO;
import fr_scapartois_auto.chariot_inspector.battery_usage.mapper.BatteryUsageMapperImpl;
import fr_scapartois_auto.chariot_inspector.battery_usage.repositorie.BatteryUsageRepository;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.repositories.CartRepository;
import fr_scapartois_auto.chariot_inspector.issue.beans.Issue;
import fr_scapartois_auto.chariot_inspector.issue.mappers.IssueMapper;
import fr_scapartois_auto.chariot_inspector.issue.mappers.IssueMapperImpl;
import fr_scapartois_auto.chariot_inspector.issue.repositories.IssueRepository;
import fr_scapartois_auto.chariot_inspector.issue.services.IssueService;
import fr_scapartois_auto.chariot_inspector.pickup.bean.Pickup;
import fr_scapartois_auto.chariot_inspector.pickup.repositorie.PickupRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BatteryUsageService implements Webservices<BatteryUsageDTO> {

    private final BatteryRepository batteryRepository;

    private final CartRepository cartRepository;

    private final BatteryUsageRepository batteryUsageRepository;

    private final BatteryUsageMapper batteryUsageMapper = new BatteryUsageMapperImpl();

    private final PickupRepository pickupRepository;

    private final BatteryMapper batteryMapper = new BatteryMapperImpl();

    private final IssueService issueService;

    private final IssueMapper issueMapper = new IssueMapperImpl();

    private final IssueRepository issueRepository;

    @Override
    public Page<BatteryUsageDTO> all(Pageable pageable) {
        return this.batteryUsageRepository.findAll(pageable)
                .map(this.batteryUsageMapper::fromBatteryUsage);
    }

    @Override
    public BatteryUsageDTO add(BatteryUsageDTO e) {

        BatteryUsage batteryUsage = this.batteryUsageMapper.fromBatteryUsageDTO(e);

        Optional<Battery> battery = this.batteryRepository.findById(batteryUsage.getBattery().getIdBattery());
        Optional<Cart> cart = this.cartRepository.findById(batteryUsage.getCart().getIdCart());

        if (battery.isPresent() && cart.isPresent())
        {
            String workSessionId = this.getWorkSessionByCart(cart.get());

            batteryUsage.setWorkSessionId(workSessionId);
            batteryUsage.setCart(cart.get());
            batteryUsage.setBattery(battery.get());

            BatteryUsage savedBatteryUsage = this.batteryUsageRepository.save(batteryUsage);

            this.checkAndCreateIssues(savedBatteryUsage);

            return this.batteryUsageMapper.fromBatteryUsage(savedBatteryUsage);

        }else {
            throw new RuntimeException("Cart or Battery not found");
        }
    }

    private void checkAndCreateIssues(BatteryUsage batteryUsage) {
        if ("MAUVAIS".equalsIgnoreCase(batteryUsage.getState())) {
            Optional<Pickup> latestPickup = this.pickupRepository.findByCart(batteryUsage.getCart()).stream()
                    .max(Comparator.comparing(Pickup::getPickupDateTime));

            if (latestPickup.isPresent()) {
                String description = "La batterie est dans un état : 'MAUVAIS': " + batteryUsage.getCart().getIdCart();

                // Rechercher les issues existantes pour cette session de travail
                List<Issue> existingIssues = this.issueRepository.findByWorkSessionId(batteryUsage.getWorkSessionId());
                Issue issue;

                if (!existingIssues.isEmpty()) {
                    // S'il existe déjà une issue, on concatène la description
                    issue = existingIssues.get(0);
                    issue.setDescription(issue.getDescription() + " " + description);
                } else {
                    // Sinon, on crée une nouvelle issue
                    issue = Issue.builder()
                            .description(description)
                            .account(latestPickup.get().getAccount())
                            .workSessionId(batteryUsage.getWorkSessionId())
                            .createdAt(LocalDateTime.now())
                            .build();
                }

                this.issueService.add(this.issueMapper.fromIssue(issue));
            } else {
                throw new RuntimeException("No Pickup found for the cart associated with this Battery Usage.");
            }
        }
    }




    @Override
    public BatteryUsageDTO update(Long id, BatteryUsageDTO e) {
        return null;
    }

    @Override
    public void remove(Long id) {
        Optional<BatteryUsage> batteryUsage = this.batteryUsageRepository.findById(id);

        if (batteryUsage.isEmpty())
            throw new RuntimeException("Battery usage with id : "+id+ " was not found");

        this.batteryUsageRepository.delete(batteryUsage.get());
    }

    @Override
    public Optional<BatteryUsageDTO> getById(Long id) {
        return Optional.empty();
    }

    public String getWorkSessionByCart(Cart cart)
    {
        List<Pickup> pickups = this.pickupRepository.findByCart(cart);

        if (pickups.isEmpty()) {
            throw new RuntimeException("No pickup found for cart");
        }

        Pickup latestPickup = pickups.stream()
                .max(Comparator.comparing(Pickup::getPickupDateTime))
                .orElseThrow(() -> new RuntimeException("No pickup found for cart"));

        return latestPickup.getWorkSessionId();
    }

    public List<BatteryUsageDTO> allBatteryUsageByWorkSessionId(String workSessionId)
    {
        return this.batteryUsageRepository.findByWorkSessionId(workSessionId)
                .stream()
                .map(this.batteryUsageMapper::fromBatteryUsage)
                .collect(Collectors.toList());
    }

    public BatteryDTO getBatteryById(Long batteryId) {
        return batteryRepository.findById(batteryId)
                .map(batteryMapper::fromBattery)
                .orElseThrow(() -> new RuntimeException("Battery not found"));
    }

    public List<BatteryUsageDTO> getBatteryUsageByCartId(Long cartId) {

        Optional<Cart> cart = this.cartRepository.findById(cartId);

        if (cart.isEmpty())
            throw new RuntimeException("Cart with id : " +cartId+ " was not found");

        List<BatteryUsage> batteryUsages = this.batteryUsageRepository.findByCart(cart.get());
        return batteryUsages.stream()
                .map(batteryUsageMapper::fromBatteryUsage)
                .collect(Collectors.toList());
    }

}
