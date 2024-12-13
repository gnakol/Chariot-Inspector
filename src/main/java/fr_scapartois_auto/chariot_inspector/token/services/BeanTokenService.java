package fr_scapartois_auto.chariot_inspector.token.services;

import fr_scapartois_auto.chariot_inspector.token.beans.Token;
import fr_scapartois_auto.chariot_inspector.token.dtos.TokenDTO;
import fr_scapartois_auto.chariot_inspector.token.mappers.TokenMapper;
import fr_scapartois_auto.chariot_inspector.token.mappers.TokenMapperImpl;
import fr_scapartois_auto.chariot_inspector.token.repositories.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BeanTokenService {

    private final TokenRepository tokenRepository;

    private final TokenMapper tokenMapper = new TokenMapperImpl();

    public Page<TokenDTO> allToken(Pageable pageable)
    {
        return this.tokenRepository.findAll(pageable)
                .map(this.tokenMapper::fromToken);
    }

    public void removeTokenById(Long idToken)
    {
        Optional<Token> token = this.tokenRepository.findById(idToken);

        if (token.isEmpty())
            throw new RuntimeException("Token wiht id : "+idToken+ " was not found");

        this.tokenRepository.delete(token.get());
    }

    @Transactional
    public void removeTokenByRange(Long startId, Long endId)
    {
        Optional<Token> startToken = this.tokenRepository.findById(startId);
        Optional<Token> endToken = this.tokenRepository.findById(endId);

        if (startToken.isPresent() && endToken.isPresent())
            this.tokenRepository.deleteByIdRange(startId, endId);
    }

    @Transactional
    public void removeTokenByCheckId(List<Long> ids)
    {
        this.tokenRepository.deleteByIds(ids);
    }



}
