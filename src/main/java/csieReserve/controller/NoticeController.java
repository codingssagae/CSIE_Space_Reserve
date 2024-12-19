package csieReserve.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NoticeController {

    @Operation(summary = "공지사항 조회 API",
            description = "지금까지 저장된 모든 공지사항 조회 API")
    @GetMapping("/getNotice")
    public void getNotice() {
    }
}
