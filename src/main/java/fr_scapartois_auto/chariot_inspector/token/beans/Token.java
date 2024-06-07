package fr_scapartois_auto.chariot_inspector.token.beans;

import fr_scapartois_auto.chariot_inspector.account.beans.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Long idToken;

    @Column(name = "value_token")
    private String valueToken;

    @Column(name = "status_token")
    private Boolean statusToken ;

    @Column(name = "expiration_token")
    private Boolean expirationToken;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "id_account")
    private Account account;

}
