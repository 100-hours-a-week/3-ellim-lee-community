import { PostAPI } from "/js/api/posts.js";
import { ImageAPI } from "/js/api/images.js";

/** 게시물 작성 API 호출 및 데이터 처리 시작 */

const postCreateForm = document.getElementById("post-create-form");

postCreateForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(postCreateForm);
    const title = formData.get("title");
    const content = formData.get("content");
    const imageFiles = formData.getAll("postImages");

    if (imageFiles.length > 5) {
        alert("이미지는 최대 5개까지 업로드할 수 있습니다.");
        return;
    }

    let imageIds = [];
    for (const imageFile of imageFiles) {
        if (imageFile && imageFile.size > 0) {
            const imageUploadResponse = await ImageAPI.uploadPostImage(imageFile);
            const imageId = imageUploadResponse.data.imageId;
            if (imageId) {
                imageIds.push(imageId);
            }
        }
    }

    try {
        await PostAPI.createPost(title, content, imageIds);
        alert("게시물이 성공적으로 작성되었습니다. 게시물 목록 페이지로 이동합니다.");
        window.location.href = "/posts";
    } catch (error) {
        console.error("게시물 작성 중 오류 발생:", error);
        alert("게시물 작성에 실패했습니다. 다시 시도해주세요.");
    }
});

/** 게시물 작성 API 호출 및 데이터 처리 끝 */