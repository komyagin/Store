package com.store.admin.user.controller;

import com.store.admin.FileUploadUtil;
import com.store.admin.security.StoreUserDetails;
import com.store.admin.user.UserService;
import com.store.common.entity.Role;
import com.store.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String viewAccountDetails(@AuthenticationPrincipal StoreUserDetails loggerUser,
                                     Model model) {
        String email = loggerUser.getUsername();
        User user = userService.getUserByEmail(email);
        List<Role> roleList = userService.roleList();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", roleList);
        model.addAttribute("pageTitle", "Account info");
        return "users/account_form";
    }

    @PostMapping("/account/update")
    public String updateAccountDetails(User user, RedirectAttributes redirectAttributes,
                                       @AuthenticationPrincipal StoreUserDetails loggedUser,
                                       @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            user.setPhotos(fileName);
            User savedUser = userService.updateUserAccount(user);
            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty()) user.setPhotos(null);
            userService.updateUserAccount(user);
        }
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        redirectAttributes.addFlashAttribute("message",
                "Your account details have been updated successfully");
        return "redirect:/account";
    }

}
