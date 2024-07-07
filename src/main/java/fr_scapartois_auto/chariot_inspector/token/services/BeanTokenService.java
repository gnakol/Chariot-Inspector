package fr_scapartois_auto.chariot_inspector.token.services;

import fr_scapartois_auto.chariot_inspector.token.beans.Token;
import fr_scapartois_auto.chariot_inspector.token.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeanTokenService {

    private final TokenRepository tokenRepository;

    public void removeToken()
    {
        List<Token> tokens = this.tokenRepository.findAll();

        for (int i = 0; i < tokens.size(); i++)
        {
            if (i != tokens.size() - 2)
                this.tokenRepository.delete(tokens.get(i));
        }
    }
}
