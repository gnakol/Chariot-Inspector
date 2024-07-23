package fr_scapartois_auto.chariot_inspector.pickup.service;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.dtos.CartDTO;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapper;
import fr_scapartois_auto.chariot_inspector.cart.mappers.CartMapperImpl;
import fr_scapartois_auto.chariot_inspector.cart.repositories.CartRepository;
import fr_scapartois_auto.chariot_inspector.pickup.bean.Pickup;
import fr_scapartois_auto.chariot_inspector.pickup.dto.PickupDTO;
import fr_scapartois_auto.chariot_inspector.pickup.mapper.PickupMapper;
import fr_scapartois_auto.chariot_inspector.pickup.mapper.PickupMapperImpl;
import fr_scapartois_auto.chariot_inspector.pickup.repositorie.PickupRepository;
import fr_scapartois_auto.chariot_inspector.session.service.WorkSessionService;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PickupService implements Webservices<PickupDTO> {

    private final PickupRepository pickupRepository;

    private final PickupMapper pickupMapper = new PickupMapperImpl();

    private final AccountRepository accountRepository;

    private final CartRepository cartRepository;

    private final CartMapper cartMapper = new CartMapperImpl();

    private final WorkSessionService workSessionService;


    @Override
    public Page<PickupDTO> all(Pageable pageable) {
        return this.pickupRepository.findAll(pageable)
                .map(this.pickupMapper::fromPickup);
    }

    @Override
    public PickupDTO add(PickupDTO e) {

        Pickup pickup = this.pickupMapper.fromPickupDTO(e);

        Optional<Account> account = this.accountRepository.findById(pickup.getAccount().getIdAccount());
        Optional<Cart> cart = this.cartRepository.findById(pickup.getCart().getIdCart());

        if (account.isPresent() && cart.isPresent())
        {
            String workSessionId = this.workSessionService.getActiveWorkSession(account.get().getIdAccount())
                            .orElseThrow(() -> new RuntimeException("No active work session found"))
                                    .getWorkSessionId();

            pickup.setAccount(account.get());
            pickup.setCart(cart.get());
            pickup.setWorkSessionId(workSessionId);

            Pickup savedPickup = this.pickupRepository.save(pickup);

            return this.pickupMapper.fromPickup(savedPickup);
        }
        else
            throw new RuntimeException("Cart or Account not found");


    }

    @Override
    public PickupDTO update(Long id, PickupDTO e) {

        Pickup beanPickup = this.pickupMapper.fromPickupDTO(e);

        return this.pickupMapper.fromPickup(this.pickupRepository.findById(id)
                .map(pickup -> {

                    if (e.getPickupDateTime() != null)
                        pickup.setPickupDateTime(e.getPickupDateTime());

                    if (e.getReturnDateTime() != null)
                        pickup.setReturnDateTime(e.getReturnDateTime());

                    if (e.getAccountId() != null)
                    {
                        Optional<Account> account = this.accountRepository.findById(beanPickup.getAccount().getIdAccount());

                        pickup.setAccount(account.get());
                    }

                    if (e.getCartId() != null)
                    {
                        Optional<Cart> cart = this.cartRepository.findById(beanPickup.getCart().getIdCart());

                        pickup.setCart(cart.get());
                    }

                    if (e.getConditionChassis() != null)
                        pickup.setConditionChassis(e.getConditionChassis());

                    if (e.getWheelsTornPlat() != null)
                        pickup.setWheelsTornPlat(e.getWheelsTornPlat());

                    if (e.getBatteryCablesSockets() != null)
                        pickup.setBatteryCablesSockets(e.getBatteryCablesSockets());

                    if (e.getCleanNonSlipPlatform() != null)
                        pickup.setCleanNonSlipPlatform(e.getCleanNonSlipPlatform());

                    if (e.getWindshield() != null)
                        pickup.setWindshield(e.getWindshield());

                    if (e.getGasBlockStrap() != null)
                        pickup.setGasBlockStrap(e.getGasBlockStrap());

                    if (e.getForwardReverseControl() != null)
                        pickup.setForwardReverseControl(e.getForwardReverseControl());

                    if (e.getHonk() != null)
                        pickup.setHonk(e.getHonk());

                    if (e.getFunctionalElevationSystem() != null)
                        pickup.setFunctionalElevationSystem(e.getFunctionalElevationSystem());

                    if (e.getEmergencyStop() != null)
                        pickup.setEmergencyStop(e.getEmergencyStop());

                    if (e.getNoLeak() != null)
                        pickup.setNoLeak(e.getNoLeak());

                    if (e.getAntiCrushButton() != null)
                        pickup.setAntiCrushButton(e.getAntiCrushButton());

                    if (e.getConditionForks() != null)
                        pickup.setConditionForks(e.getConditionForks());

                    return this.pickupRepository.save(pickup);
                })
                .orElseThrow(() -> new RuntimeException("Account or Cart is not found")));
    }

    // *************************** For remove ******************************

    @Override
    public void remove(Long id) {

        Optional<Pickup> pickup = this.pickupRepository.findById(id);

        if (pickup.isEmpty())
            throw new RuntimeException("Pickup with id : " +id+ " was not found");

        this.pickupRepository.delete(pickup.get());

    }

    @Transactional
    public void removePickupIdRange(Long startId, Long endId)
    {
        this.pickupRepository.deleteByIdRange(startId, endId);
    }

    @Transactional
    public void removePickupByChooseId(List<Long> listIdPickup)
    {
        this.pickupRepository.deleteByIds(listIdPickup);
    }

    // ***************** *********************************************

    @Override
    public Optional<PickupDTO> getById(Long id) {
        return this.pickupRepository.findById(id)
                .map(this.pickupMapper::fromPickup);
    }

    public Page<CartDTO> allCartByAccount(Long idAccount, Pageable pageable)
    {
        Optional<Account> account = this.accountRepository.findById(idAccount);

        if (account.isEmpty())
            throw  new RuntimeException("Account with id : " +idAccount+ " was not found");

        List<Pickup> pickups = this.pickupRepository.findByAccount(account.get());

        List<Cart> carts = pickups.stream().map(Pickup::getCart).collect(Collectors.toList());

        List<CartDTO> cartDTOS = carts.stream().map(this.cartMapper::fromCart).collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), cartDTOS.size());
        int end = Math.min(start + pageable.getPageSize(), cartDTOS.size());

        return new PageImpl<>(cartDTOS.subList(start, end), pageable, cartDTOS.size());
    }

    public Page<PickupDTO> getPickupByAccountId(Long idAccount, Pageable pageable)
    {
        Optional<Account> account = this.accountRepository.findById(idAccount);

        if (account.isEmpty())
            throw new RuntimeException("Account with id : " +idAccount+ " was not found");

        List<Pickup> pickups = this.pickupRepository.findByAccount(account.get());
        List<PickupDTO> pickupDTOS = pickups.stream().map(this.pickupMapper::fromPickup).collect(Collectors.toList());

        int start = Math.min((int) pageable.getOffset(), pickupDTOS.size());
        int end = Math.min((start + pageable.getPageSize()), pickupDTOS.size());

        List<PickupDTO> pickupDTOList = pickupDTOS.subList(start, end);

        return new PageImpl<>(pickupDTOList, pageable, pickupDTOS.size());
    }

    public List<PickupDTO> allPickupByWorkSessionId(String workSessionId)
    {
        return this.pickupRepository.findByWorkSessionId(workSessionId)
                .stream()
                .map(this.pickupMapper::fromPickup)
                .collect(Collectors.toList());
    }

    public List<String> getWorkSessionIdsByAccountId(Long idAccount)
    {
        return this.pickupRepository.findDistinctWorkSessionIdsByAccountId(idAccount);
    }

    @Transactional
    public List<Long> getIdPickupByCartNumber(String cartNumber) {
        return this.pickupRepository.findIdByCartNumber(cartNumber);
    }


}
