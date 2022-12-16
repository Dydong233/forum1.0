package com.dydong.controller;

import com.alibaba.fastjson.JSONObject;
import com.dydong.mapper.ArticleMapper;
import com.dydong.mapper.UserMapper;
import com.dydong.pojo.Article;
import com.dydong.pojo.Comment;
import com.dydong.pojo.User;
import com.dydong.service.ArticleServiceImpl;
import com.dydong.service.CommentServiceImpl;
import com.dydong.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Controller
public class ArticleController {

    @Autowired
    ArticleServiceImpl articleService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    CommentServiceImpl commentService;

    @RequestMapping("/article")
    public String article(Model model){
        List<Article> articles = articleService.queryArticles();
        model.addAttribute("articles",articles);
        return "article";
    }

    @RequestMapping("/articleshowbyname")
    public String articleShowByName(String queryName,Model model){
        List<Article> articles = articleService.getArticleByAuthor(queryName);
        if(articles.size()==0){
            model.addAttribute("msg","无法查询该作者文章");
            articles = articleService.queryArticles();
            model.addAttribute("articles",articles);
            return "article";
        }
        model.addAttribute("articles",articles);
        return "article";
    }

    @PostMapping("/user/addArticle")
    public String addArticle(Article article){
        if (Security.getUsername()==null)  return "redirect:/user/noauth";
        articleService.addArticle(article);
        return "redirect:/user/welcome";
    }

    @RequestMapping("/user/file/upload")
    @ResponseBody
    public JSONObject fileUpload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
        String path = System.getProperty("user.dir") + "/upload/";
        Calendar instance = Calendar.getInstance();
        String month = (instance.get(Calendar.MONTH) + 1) + "_month";
        path = path + month;
        File realPath = new File(path);
        if (!realPath.exists()) {
            realPath.mkdirs();
        }
        //上传文件地址
        System.out.println("上传文件保存地址：" + realPath);

        //解决文件名字问题：我们使用uuid;
        String filename = "pg-" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";
        File newfile = new File(realPath, filename);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newfile);

        //给editormd进行回调
        JSONObject res = new JSONObject();
        res.put("url", "/upload/" + month + "/" + filename);
        res.put("success", 1);
        res.put("message", "upload success!");
        System.out.println(res);
        return res;
    }

    @RequestMapping("/articleshow/{id}")
    public String articleShow(Model model,@PathVariable("id") int id){
        Article article = articleService.getArticleById(id);
        model.addAttribute("article",article);
        String author = article.getAuthor();
        User user = userService.queryUserByName(author);
        model.addAttribute("user",user);
        List<Comment> comments = commentService.queryByAid(id);
        for (Comment comment : comments) {
            String username = comment.getAuthor();
            User user1 = userService.queryUserByName(username);
            comment.setPic(user1.getPic());
        }
        int size = comments.size();
        model.addAttribute("size",size);
        model.addAttribute("comments",comments);
        List<Article> articles = articleService.queryArticles();
        if(articles.size()<=3) model.addAttribute("articles",articles);
        else{
            articles=articles.subList(0,3);
            model.addAttribute("articles",articles);
        }
        return "articleshow";
    }

    @GetMapping("/user/updateArticle")
    public String UpdateArticle(@RequestParam("id") int id,Model model){
        if (Security.getUsername()==null)  return "redirect:/user/noauth";
        Article article = articleService.getArticleById(id);
        model.addAttribute("article",article);
        return "user/update";
    }

    @PostMapping("/user/updateArticle")
    public String UpdateArticle(Article article){
        if (Security.getUsername()==null)  return "redirect:/user/noauth";
        articleService.updateArticle(article);
        return "redirect:/user/selfarticle";
    }

    @RequestMapping("/user/deletearticle/{id}")
    public String deleteArticle(@PathVariable("id") int id){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String username = (String) session.getAttribute("username");
        if(username==null)  return "redirect:/user/noauth";
        String invitation = (String) session.getAttribute("invitation");
        Article article = articleService.getArticleById(id);
        if(article.getAuthor().equals(username)||invitation.equals("root")){
            articleService.deleteArticleById(id);
            return "redirect:/user/selfarticle";
        }
        return "redirect:/user/noauth";
    }

}
