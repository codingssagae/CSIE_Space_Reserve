package csieReserve.controller;

import csieReserve.dto.FAQRequestDTO;
import csieReserve.dto.FAQResponseDTO;
import csieReserve.service.FAQService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FAQController {

    private final FAQService faqService;

    @Operation(summary = "질의응답(FAQ) 조회 API",
            description = "DB에 저장된 모든 질의응답 조회 API")
    @GetMapping("/faq/getAll")
    public List<FAQResponseDTO> getAllFAQs(){
        return faqService.getAllFAQs();
    }

    @Operation(summary = "질의응답(FAQ) 생성 API",
            description = "질문과 답변을 파라미터로 전송 후 DB에 저장")
    @PostMapping("/faq/create")
    public FAQResponseDTO createFAQ(@RequestBody FAQRequestDTO faqRequestDTO){
        return faqService.createFAQ(faqRequestDTO);
    }

    @Operation(summary = "질의응답(FAQ) 수정 API",
            description = "질문과 답변을 파라미터로 전송 후 DB에 저장, 만약 답변을 수정해야 할 경우 기존의 제목을 다시 입력해야함")
    @PutMapping("/faq/update/{id}")
    public FAQResponseDTO updateFAQ(@PathVariable Long id,
                                    @RequestBody FAQRequestDTO updateFAQ){
        return faqService.updateFAQ(id,updateFAQ);
    }

    @Operation(summary = "질의응답(FAQ) 삭제 API",
            description = "질의응답의 고유 id를 앤드포인트에 넣은 후 해당 질의응답을 DB에서 삭제")
    @DeleteMapping("/faq/delete/{id}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Long id) {
        if (faqService.deleteFAQ(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
