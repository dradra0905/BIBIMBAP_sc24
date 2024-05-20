package com.withSchool.controller.user;

import com.withSchool.dto.school.ReqNoticeDTO;
import com.withSchool.dto.school.ResNoticeDTO;
import com.withSchool.dto.user.BasicUserInfoDTO;
import com.withSchool.entity.classes.ClassInformation;
import com.withSchool.entity.school.SchoolNotice;
import com.withSchool.service.classes.ClassNoticeService;
import com.withSchool.service.classes.ClassService;
import com.withSchool.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@EnableAutoConfiguration
@RequestMapping("/classes")
public class ClassController {
    private final ClassService classService;
    private final ClassNoticeService classNoticeService;
    private final UserService userService;


    @GetMapping("/{classId}")
    @Operation(summary = "유저의 반 정보 조회")
    public ResponseEntity<Map<String, Object>> getClassById(@PathVariable Long classId) {
        Map<String, Object> response = new HashMap<>();

        Optional<ClassInformation> classInfo = classService.getClassById(classId);
        List<BasicUserInfoDTO> basicUserInfoDTOS = userService.findAllClassInformation_ClassId();

        response.put("class", classInfo);
        response.put("users", basicUserInfoDTOS);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/myClass")
    @Operation(summary = "자기 반 pk 가져오기")
    public ResponseEntity<Long> findMyClass(){
        return ResponseEntity.ok().body(userService.getCurrentUserClassId());
    }

    @PostMapping("/notices")
    @Operation(summary = "교사의 반 공지 작성", description = "교사는 반 공지를 작성할 수 있다.")
    public ResponseEntity<ResNoticeDTO> createNotice(@ModelAttribute ReqNoticeDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(classNoticeService.save(request));
    }
    @PatchMapping("/notices/{notice-id}")
    @Operation(summary = "교사의 반 공지 수정", description = "교사는 반 공지를 수정할 수 있다.")
    public ResponseEntity<ResNoticeDTO> modifyOneNotice(@PathVariable(name = "notice-id") Long noticeId, @ModelAttribute ReqNoticeDTO request){

        return ResponseEntity.ok()
                .body(classNoticeService.updateById(noticeId, request));
    }
    @GetMapping("/notices/{noticeId}")
    @Operation(summary = "유저의 반 공지 상세 조회")
    public ResponseEntity<ResNoticeDTO> showOneNotice(@PathVariable(name = "noticeId") Long noticeId) {
        ResNoticeDTO resNoticeDTO = classNoticeService.findById(noticeId);
        return ResponseEntity.ok()
                .body(resNoticeDTO);
    }

    @GetMapping("/notices")
    @Operation(summary = "유저의 반 공지 리스트 조회")
    public ResponseEntity<List<ResNoticeDTO>> showAllNotices() {
        List<ResNoticeDTO> classNoticeDTOS = classNoticeService.findAll();
        return ResponseEntity.ok()
                .body(classNoticeDTOS);
    }

    @DeleteMapping("/notices/{notice-id}")
    @Operation(summary = "교사의 반 공지 삭제", description = "교사는 PK를 사용하여 반 공지를 삭제할 수 있다. ")
    public ResponseEntity<String> deleteOneNotice(@PathVariable(name = "notice-id") Long noticeId) {

        classNoticeService.deleteById(noticeId);

        return ResponseEntity.ok()
                .body("delete success");
    }


}
