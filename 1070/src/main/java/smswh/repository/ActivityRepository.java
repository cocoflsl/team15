package smswh.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import smswh.domain.Member;
import smswh.domain.activity.Activity;
import smswh.domain.activity.ActivityStatus;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {

    private final EntityManager em;

    public Long save(Activity activity) {
        em.persist(activity);
        activity.setStatus(ActivityStatus.ENROLLED);
        return activity.getId();
    }

    public void remove(Activity activity) {
        em.remove(activity);
    }

    public void add(Activity activity, Member member) {
        Activity findActivity = em.find(Activity.class, activity.getId());
        findActivity.getApplyMembers().add(member);
    }

    public Activity findById(Long id) {
        return em.find(Activity.class, id);
    }

    public Activity findByTitle(String title) {
        return em.find(Activity.class, title);
    }

}
