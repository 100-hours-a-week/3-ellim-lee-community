import { apiRequest } from "./base.js";

export const UserAPI = {
    signin: (email, password) => 
        apiRequest('/auth', {
            method: 'POST',
            body: JSON.stringify({ email, password }),
        }),
    signup: (email, password, password2, nickname, image_id) =>
        apiRequest('/users', {
            method: 'POST',
            body: JSON.stringify({ email, password, password2, nickname, image_id: image_id ?? null }),
        }),
    signout: () => 
        apiRequest('/auth', { 
            method: 'DELETE' 
        }),
    getCurrentUser: () => 
        apiRequest('/users/me', { 
            method: 'GET' 
        }),
    getUserById: (user_id) => 
        apiRequest(`/users/${user_id}`, { 
            method: 'GET' 
        }),
    updateCurrentUser: (nickname, profileImageId) => {
            const body = {};
            if (nickname !== undefined && nickname !== null) body.nickname = nickname;
            if (profileImageId !== undefined && profileImageId !== null) body.image_id = profileImageId;

            return apiRequest('/users/me', {
                method: 'PATCH',
                body: JSON.stringify(body),
            });
        },
    updateCurrentUserPassword: (newPassword, newPassword2) => 
        apiRequest('/users/me/password', {
            method: 'PATCH',
            body: JSON.stringify({ newPassword, newPassword2 }),
        }),
    deleteCurrentUser: () => 
        apiRequest('/users/me', {
            method: 'DELETE',
        }),
}