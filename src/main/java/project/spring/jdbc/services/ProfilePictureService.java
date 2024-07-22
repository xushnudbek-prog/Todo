package project.spring.jdbc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.spring.jdbc.dao.ProfilePictureDao;
import project.spring.jdbc.domains.ProfilePicture;

@Service
@RequiredArgsConstructor
public class ProfilePictureService {
    private final ProfilePictureDao profilePictureDao;

    public String getProfilePictureUrl(int profileId) {
        ProfilePicture profilePicture = profilePictureDao.getById(profileId);
        return "/home/xushnudbek/Desktop/uploads/" + profilePicture.getGeneratedName() + "." +StringUtils.getFilenameExtension(profilePicture.getOriginalName());
    }

}
