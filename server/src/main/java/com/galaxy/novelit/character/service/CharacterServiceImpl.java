package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.repository.CharacterRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final MongoTemplate mongoTemplate;

    @Transactional(readOnly = true)
    @Override
    public CharacterDtoRes getCharacterInfo(String characterUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);

//        삭제된 캐릭터 처리
//        if (character.isDeleted()) {
//            return null;
//        }

        CharacterDtoRes dto = new CharacterDtoRes();
        dto.setGroupUUID(character.getGroupUUID());
        dto.setCharacterName(character.getCharacterName());
        dto.setCharacterUUID(character.getCharacterUUID());
        dto.setInformation(character.getInformation());
        dto.setDescription(character.getDescription());
        dto.setRelationship(character.getRelationship());
        dto.setCharacterImage(character.getCharacterImage());
        dto.setDeleted(character.isDeleted());
        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterSimpleDtoRes> getCharacters(String groupUUID) {
        List<CharacterEntity> characters = characterRepository.findAllByGroupUUID(groupUUID);
        List<CharacterSimpleDtoRes> dto = new ArrayList<>();

        for (CharacterEntity character : characters) {
            CharacterSimpleDtoRes characterSimpleDtoRes = CharacterSimpleDtoRes.builder()
                .characterUUID(character.getCharacterUUID())
                .characterName(character.getCharacterName())
                .information(character.getInformation())
                .characterImage(character.getCharacterImage()).build();
            dto.add(characterSimpleDtoRes);
        }

        return dto;
    }

    @Transactional
    @Override
    public void createCharacter(CharacterDtoReq dto) {
        String characterUUID = UUID.randomUUID().toString();
//        String characterName = dto.getCharacterName();
//        String description = dto.getDescription();
//        Map<String, String> information = dto.getInformation();
//        Map<String, String> relationship = dto.getRelationship();

        CharacterEntity newCharacter = CharacterEntity.builder()
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(dto.getRelationship()).build();


//        CharacterEntity.CharacterEntityBuilder builder = CharacterEntity.builder()
//            .characterUUID(characterUUID)
//            .characterName(characterName)
//            .description(description)
//            .information(information)
//            .relationship(relationship);
//        CharacterEntity character = builder.build();

        characterRepository.save(newCharacter);
    }

    @Transactional
    @Override
    public void updateCharacter(CharacterDtoReq dto) {
        CharacterEntity character = characterRepository.findByCharacterUUID(dto.getCharacterUUID());
        CharacterEntity newCharacter = CharacterEntity.builder()
            .characterId(character.getCharacterId())
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(dto.getRelationship()).build();

        characterRepository.save(newCharacter);

    }

    @Transactional
    @Override
    public void deleteCharacter(String characterUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        CharacterEntity newCharacter = CharacterEntity.builder()
            .characterId(character.getCharacterId())
            .isDeleted(true).build();

        characterRepository.save(newCharacter);
    }
}
