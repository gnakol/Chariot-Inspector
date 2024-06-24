package fr_scapartois_auto.chariot_inspector.pickup.service;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import fr_scapartois_auto.chariot_inspector.account.repositories.AccountRepository;
import fr_scapartois_auto.chariot_inspector.cart.beans.Cart;
import fr_scapartois_auto.chariot_inspector.cart.repositories.CartRepository;
import fr_scapartois_auto.chariot_inspector.pickup.bean.Pickup;
import fr_scapartois_auto.chariot_inspector.pickup.dto.PickupDTO;
import fr_scapartois_auto.chariot_inspector.pickup.mapper.PickupMapper;
import fr_scapartois_auto.chariot_inspector.pickup.mapper.PickupMapperImpl;
import fr_scapartois_auto.chariot_inspector.pickup.repositorie.PickupRepository;
import fr_scapartois_auto.chariot_inspector.webservices.Webservices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PickupService implements Webservices<PickupDTO> {

    private final PickupRepository pickupRepository;

    private final PickupMapper pickupMapper = new PickupMapperImpl();

    private final AccountRepository accountRepository;

    private final CartRepository cartRepository;


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
            pickup.setAccount(account.get());
            pickup.setCart(cart.get());

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

                    return this.pickupRepository.save(pickup);
                })
                .orElseThrow(() -> new RuntimeException("Account or Cart is not found")));
    }

    @Override
    public void remove(Long id) {

        Optional<Pickup> pickup = this.pickupRepository.findById(id);

        if (pickup.isEmpty())
            throw new RuntimeException("Pickup with id : " +id+ " was not found");

        this.pickupRepository.delete(pickup.get());

    }

    @Override
    public Optional<PickupDTO> getById(Long id) {
        return this.pickupRepository.findById(id)
                .map(this.pickupMapper::fromPickup);
    }

}
