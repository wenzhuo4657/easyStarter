package cn.wenzhuo4657.db.router;

import cn.wenzhuo4657.db.router.annotation.DBRouter;

public interface IUserDao {

    @DBRouter(key = "userId")
    void insertUser(String req);

}