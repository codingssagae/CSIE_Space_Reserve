package csieReserve.service;

import csieReserve.Repository.FAQRepository;
import csieReserve.domain.for_admin.FAQ;
import csieReserve.dto.FAQRequestDTO;
import csieReserve.dto.FAQResponseDTO;
import csieReserve.mapper.FAQMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FAQService {

    private final FAQRepository faqRepository;
    private final FAQMapper faqMapper;

    // FAQ 전체 조회
    public List<FAQResponseDTO> getAllFAQs(){
        return faqRepository.findAll().stream()
                .map(faqMapper::toDTO)
                .collect(Collectors.toList());
    }

    // FAQ 생성
    @Transactional
    public FAQResponseDTO createFAQ(FAQRequestDTO requestDTO){
        FAQ faq = faqMapper.toEntity(requestDTO);
        faqRepository.save(faq);
        return faqMapper.toDTO(faq);
    }

    // FAQ 수정
    @Transactional
    public FAQResponseDTO updateFAQ(Long id, FAQRequestDTO updatedFAQ){
        Optional<FAQ> faqOptional = faqRepository.findById(id);
        FAQ faq = faqOptional.get();
        faq.setQuestion(updatedFAQ.getQuestion());
        faq.setAnswer(updatedFAQ.getAnswer());
        faqRepository.save(faq);
        return faqMapper.toDTO(faq);
    }

    // FAQ 삭제
    public boolean deleteFAQ(Long id) {
        if (faqRepository.existsById(id)) {
            faqRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
