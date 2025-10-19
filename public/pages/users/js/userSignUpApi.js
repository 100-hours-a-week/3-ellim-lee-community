import { UserAPI } from "/js/api/users.js";
import { ImageAPI } from "/js/api/images.js";

/** 회원가입 API 호출 및 데이터 처리 시작 */

const signUpForm = document.getElementById("sign-up-form");

signUpForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(signUpForm);
    const nickname = formData.get("nickname");
    const email = formData.get("email");
    const password = formData.get("password");
    const password2 = formData.get("password2");
    const profileImageFile = formData.get("profileImage");

    let profileImageId = null;
    if (profileImageFile && profileImageFile.size > 0) {
        const imageUploadResponse = await ImageAPI.uploadProfileImage(profileImageFile);
        profileImageId = imageUploadResponse.data.imageId ?? null;
    }

    try {
        await UserAPI.signUp(email, password, password2, nickname, profileImageId);
        alert("회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.");
        window.location.href = "/users/signin";
    } catch (error) {
        console.error("회원가입 중 오류 발생:", error);
        alert("회원가입에 실패했습니다. 다시 시도해주세요.");
    }
});

/** 회원가입 API 호출 및 데이터 처리 끝 */