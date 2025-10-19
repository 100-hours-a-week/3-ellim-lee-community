import { apiRequest } from "./base.js";

export const ImageAPI = {
    uploadProfileImage: (file) => {
        const formData = new FormData();
        formData.append('file', file);
        return apiRequest('/images/profile-img', {
            method: 'POST',
            body: formData,
        });
    },
    uploadPostImage: (file) => {
        const formData = new FormData();
        formData.append('file', file);
        return apiRequest('/images/post-img', {
            method: 'POST',
            body: formData,
        });
    },
}
