import { apiRequest } from "./base.js";

export const ImageAPI = {
    uploadProfileImage: (file) => {
        const formData = new FormData();
        formData.append('image', file);
        return apiRequest('/images/profile', {
            method: 'POST',
            body: formData,
        });
    },
    uploadPostImage: (file) => {
        const formData = new FormData();
        formData.append('image', file);
        return apiRequest('/images/post', {
            method: 'POST',
            body: formData,
        });
    },
}
