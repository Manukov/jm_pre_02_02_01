package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCar(String model, int series) {

        List<User> list = sessionFactory
                .getCurrentSession()
                .createQuery("select user from User user join Car car on user.car.car_id=car.car_id where car.model=:model and car.series=:series")
                .setParameter("model", model)
                .setParameter("series", series).getResultList();

        if(!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
