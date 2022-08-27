package smswh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import smswh.domain.Member;
import smswh.domain.activity.Activity;
import smswh.repository.ActivityRepository;
import smswh.web.ScriptUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static smswh.web.SessionConst.*;
import static smswh.domain.activity.ActivityStatus.SCHEDULED;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping(method = RequestMethod.POST)
public class ActivityController {

    private final ActivityRepository activityRepository;

    @PostMapping("url 입력")
    public String upload(@SessionAttribute(name = ACTIVITY) Activity activity, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Activity uploadingActivity = (Activity) request.getSession().getAttribute(ACTIVITY);

        if(uploadingActivity.getMaxAppliers() < 2) {
            ScriptUtils.alertAndBackPage(response, "인원 수는 2명 이상 되어야 합니다.");
        }
        activity.getApplyMembers().add(activity.getUploader());
        activityRepository.save(activity);
        return "리다이렉트 url";
    }

    @PostMapping ("url 입력")
    public String delete(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member loginMember, Activity activity, HttpServletResponse response) throws IOException {
        // 글을 삭제하려는 사람과 작성자가 같아야 삭제가 가능함...
        if(loginMember != activity.getUploader()) {
            ScriptUtils.alertAndBackPage(response, "작성자만 삭제 가능합니다.");
        }
        activityRepository.remove(activity);
        return "리다이렉트 url";
    }

    @PostMapping("url 입력")
    public String applyActivity(@SessionAttribute(name = LOGIN_MEMBER, required = false) Member loginMember, Activity activity, HttpServletResponse response) throws IOException {

        // 이미 신청한 회원인 경우
        if(activity.getApplyMembers().contains(loginMember))    ScriptUtils.alertAndBackPage(response, "이미 신청하셨습니다.");
        // 인원이 초과된 경우
        if(activity.getStatus().equals(SCHEDULED))  ScriptUtils.alertAndBackPage(response, "신청 가능 인원이 초과되었습니다.");

        activityRepository.add(activity, loginMember);

        return "리다이렉트 url";
    }
}
