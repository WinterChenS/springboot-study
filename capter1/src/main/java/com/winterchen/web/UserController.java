package com.winterchen.web;

import com.winterchen.domain.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 *
 * Created by winterchen on 2017/11/21.
 */
@RestController
@RequestMapping(value="/users")
public class UserController {

    //创建线程安全的Map
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());

    @ApiOperation(value="用户列表", notes = "")
    @GetMapping("/")
    public List<User> getUserList(){
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        List<User> rs = new ArrayList<>(users.values());
        return rs;
    }

    @ApiOperation(value="创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体User",required = true, dataType = "User")
    @PostMapping("/")
    public String postUser(@ModelAttribute User user){
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        users.put(user.getId(), user);
        return "success";
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据用户的id查找User")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return users.get(id);
    }

    @ApiOperation(value = "更新用户信息", notes = "根据用户的id来指定用户，然后根据传送的User更新实体User")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要更新的用户Id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "需要更新的用户详细", required = true, dataType = "User", paramType = "body")
    })
    @PostMapping("/update/{id}")
    public String putUser(@PathVariable Long id, @ModelAttribute User user){

        // 处理"/users/{id}"的PUT请求，用来更新User信息
        User u = users.get(id);
        u.setName(user.getName());
        u.setAge(user.getAge());
        users.remove(id);
        users.put(id,u);
        return "success";

    }

    @ApiOperation(value = "删除用户", notes = "根据用户ID删除指定用户")
    @ApiImplicitParam(name = "id", value = "需要删除的用户ID", required = true, paramType = "path", dataType = "Long")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        users.remove(id);
        return "success";
    }

}
