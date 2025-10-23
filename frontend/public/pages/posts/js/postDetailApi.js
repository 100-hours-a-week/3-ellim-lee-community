import { PostAPI } from "/js/api/posts.js";

const pathParts = window.location.pathname.split('/');
const postIdFromPath = pathParts[pathParts.length - 1];

/** Post Detail API 호출 및 데이터 처리 시작 */

const postDetailElement = document.querySelector(".post-detail");

let isPostLoading = false;
let postTemplate = null;
let isPostLike = null;

async function loadPostDetail(postId) {
    const response = await fetch("/pages/posts/components/postDetail.html");
    postTemplate = await response.text();
}

function renderPostDetailHTML(post){
    let html = Object.entries(post).reduce(
        (html, [key, value]) => html.replace(new RegExp(`{{${key}}}`, "g"), value ?? ""),
        postTemplate
    );

    /**
     * 게시물 작성자가 아닌 경우, 수정 및 삭제 버튼 제거
     * 현재 템플릿에서 data-post-id="{{postId}}" 속성으로 문자열이 바뀔 수 있으므로 정규표현식으로 처리
     */
    if (!post.isAuthor) {
        html = html
            .replace(/<button[^>]*class="update-post"[^>]*>[\s\S]*?<\/button>/, '')
            .replace(/<button[^>]*class="delete-post"[^>]*>[\s\S]*?<\/button>/, '');
    }
    return html;
}

async function fetchPostDetail(postId) {
    if (isPostLoading) return;
    isPostLoading = true;
    try {
        const response = await PostAPI.getPostById(postId);
        const post = response.data;
        const processedPost = {
            postId : postIdFromPath,
            title : post.title,
            postImageUrls : post.imageUrls ?? [],
            content : post.content,
            profileImageUrl : post.profileImageUrl ?? '/assets/imgs/profile_icon.svg',
            author : post.author.nickname,
            date : new Date(post.createdAt).toLocaleString('ko-KR', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit',
                second: '2-digit',
                hour12: false
            }),
            views : post.viewCount ?? 0,
            likes : post.likeCount ?? 0,
            comments : post.commentCount ?? 0,
            isAuthor : post.isAuthor,
            isLiked : post.isLiked,
        };

        isPostLike = processedPost.isLiked;

        const postHTML = renderPostDetailHTML(processedPost);

        const tempDiv = document.createElement('div');
        tempDiv.innerHTML = postHTML;

        const imageContainer = tempDiv.querySelector('.post-images');
        if (processedPost.postImageUrls.length > 0) {
            processedPost.postImageUrls.forEach(url => {
                const img = document.createElement('img');
                img.src = url;
                img.alt = 'Post Image';
                imageContainer.appendChild(img);
            });
        } else {
            imageContainer.style.display = 'none';
        }

        postDetailElement.innerHTML = tempDiv.innerHTML;
    } catch (error) {
        console.error("게시물 상세 불러오기 실패:", error);
    } finally {
        isPostLoading = false;
    }
}

await loadPostDetail();

if (postIdFromPath) {
    await fetchPostDetail(postIdFromPath);
} else {
    console.error("postId 파라미터가 없습니다.");
}

/** Post Detail API 호출 및 데이터 처리 끝 */

/** Post 수정 API 호출 및 데이터 처리 시작 */

postDetailElement.addEventListener("click", async (event) => {
    if (event.target.classList.contains("update-post")) {
        const postId = event.target.dataset.postId;
        if (!postId) return;
        
        /**
         * 현재는 간단히 prompt 창을 통해 수정 내용을 입력받아 처리
         * 추후 모달 창이나 별도의 입력 폼을 통해 수정할 수 있도록 개선 예정
        */

        const newTitle = prompt("수정할 제목을 입력하세요:");
        if (!newTitle) return;

        const newContent = prompt("수정할 내용을 입력하세요:");
        if (!newContent) return;

        try {
            await PostAPI.updatePost(postId, newTitle, newContent, null);
            window.location.reload();
        } catch (error) {
            console.error("게시물 수정 실패:", error);
            alert("게시물 수정에 실패했습니다. 다시 시도해주세요.");
        }
    }
});

/** Post 수정 API 호출 및 데이터 처리 끝 */

/** Post 삭제 API 호출 및 데이터 처리 시작 */

postDetailElement.addEventListener("click", async (event) => {
    if (event.target.classList.contains("delete-post")) {
        const postId = event.target.dataset.postId;
        if (!postId) return;

        const confirmDelete = confirm("게시물을 정말 삭제하시겠습니까?");
        if (!confirmDelete) return;

        try {
            await PostAPI.deletePost(postId);
            window.location.href = "/posts";
        } catch (error) {
            console.error("게시물 삭제 실패:", error);
            alert("게시물 삭제에 실패했습니다. 다시 시도해주세요.");
        }
    }
});
/** Post 삭제 API 호출 및 데이터 처리 끝 */

/** 댓글 리스트 API 호출 및 데이터 처리 시작 */

const commentListElement = document.querySelector(".post-comments-list");
const triggerElement = document.querySelector(".scroll-trigger");

let isCommentLoading = false;
let hasNextComments = true;
let lastCommentId = null;
let commentTemplate = null;

async function loadComments() {
    const response = await fetch("/pages/posts/components/postDetailCommentItem.html");
    commentTemplate = await response.text();
}

function renderCommentHTML(comment){
    let html = Object.entries(comment).reduce(
        (html, [key, value]) => html.replace(new RegExp(`{{${key}}}`, "g"), value ?? ""),
        commentTemplate
    );

    /**
     * 댓글 작성자가 아닌 경우, 수정 및 삭제 버튼 제거
     * 현재 템플릿에서 data-comment-id="{{commentId}}" 속성으로 문자열이 바뀔 수 있으므로 정규표현식으로 처리
     */
    if (!comment.isAuthor) {
        html = html
            .replace(/<button[^>]*class="update-comment"[^>]*>[\s\S]*?<\/button>/, "")
            .replace(/<button[^>]*class="delete-comment"[^>]*>[\s\S]*?<\/button>/, "");
    }

    return html;
}

function appendComments(comments){
    if (!Array.isArray(comments) || comments.length === 0) {
        triggerElement.textContent = "더 이상 불러올 댓글이 없습니다.";
        return;
    }
    const html = comments.map(renderCommentHTML).join("");
    commentListElement.insertAdjacentHTML("beforeend", html);
}

async function fetchComments(postId) {
    if (isCommentLoading || !hasNextComments) return;
    isCommentLoading = true;
    triggerElement.textContent = "불러오는 중...";
    try {
        const response = await PostAPI.getCommentsOnPost(postId, lastCommentId, 5);
        const { comments, lastCommentId: newLastCommentId, hasNext: newHasNext } = response.data;
        const processedComments = comments.map(comment => ({
            commentId : comment.commentId,
            content : comment.content,
            profileImageUrl : comment.profileImageUrl ?? '/assets/imgs/profile_icon.svg',
            author : comment.author.nickname,
            date : new Date(comment.createdAt).toLocaleDateString(),
            isAuthor : comment.isAuthor,
        }));

        appendComments(processedComments);

        lastCommentId = newLastCommentId;
        hasNextComments = newHasNext;

        if (!hasNextComments) {
            triggerElement.textContent = "모든 댓글을 불러왔습니다.";
        } else {
            triggerElement.textContent = "";
        }
    } catch (error) {
        console.error("댓글 불러오기 실패:", error);
    } finally {
        isCommentLoading = false;
    }
}

const observer = new IntersectionObserver(([entry]) => {
    if (entry.isIntersecting && hasNextComments && !isCommentLoading) {
        fetchComments(postIdFromPath);
    }
});

observer.observe(triggerElement);

await loadComments();
fetchComments(postIdFromPath);

/** 댓글 리스트 API 호출 및 데이터 처리 끝 */

/** 댓글 작성 API 호출 및 데이터 처리 시작 */
const commentForm = document.querySelector(".post-comment-form");
commentForm.addEventListener("submit", async (event) => {
    event.preventDefault();
    const formData = new FormData(commentForm);
    const content = formData.get("content").trim();
    if (!content) return;

    await PostAPI.createCommentOnPost(postIdFromPath, content);

    /**
     * 현재 댓글 작성 API에서 해당 댓글에 대한 Response를 주지 않으므로
     * 댓글 작성 후 새로고침을 통해 댓글 리스트를 다시 불러오는 방식으로 처리
     * 추후 API에서 작성된 댓글 데이터를 반환하도록 수정 시, 해당 부분을 수정할 예정
     */
    window.location.reload();

    // commentForm.reset();
});
/** 댓글 작성 API 호출 및 데이터 처리 끝 */

/** 댓글 수정 API 호출 및 데이터 처리 시작 */

const commentListContainer = document.querySelector(".post-comments-list");

commentListContainer.addEventListener("click", async (event) => {
    if (!event.target.classList.contains("update-comment")) return;

    const commentId = event.target.dataset.commentId;
    if (!commentId) return;

    /** 
     * 현재는 간단히 prompt 창을 통해 수정 내용을 입력받아 처리
     * 추후 모달 창이나 별도의 입력 폼을 통해 수정할 수 있도록 개선 예정
     */
    const newContent = prompt("수정할 댓글 내용을 입력하세요:");
    if (!newContent) return;

    try {
        await PostAPI.updateComment(postIdFromPath, commentId, newContent);
        window.location.reload();
    } catch (error) {
        console.error("댓글 수정 실패:", error);
        alert("댓글 수정에 실패했습니다. 다시 시도해주세요.");
    }
});

/** 댓글 수정 API 호출 및 데이터 처리 끝 */

/** 댓글 삭제 API 호출 및 데이터 처리 시작 */

commentListContainer.addEventListener("click", async (event) => {
    if (!event.target.classList.contains("delete-comment")) return;

    const commentId = event.target.dataset.commentId;
    if (!commentId) return;

    /**
     * 현재는 간단히 confirm 창을 통해 삭제 여부를 확인
     * 추후 모달 창을 통해 삭제 여부를 확인할 수 있도록 개선 예정
     */
    const confirmed = confirm("정말로 이 댓글을 삭제하시겠습니까?");
    if (!confirmed) return;

    try {
        await PostAPI.deleteComment(postIdFromPath, commentId);

        /**
         * 현재 댓글 삭제 API에서 해당 댓글에 대한 Response를 주지 않으므로
         * 댓글 삭제 후 새로고침을 통해 댓글 리스트를 다시 불러오는 방식으로 처리
         * 추후 API에서 삭제된 댓글 데이터를 반환하도록 수정 시, 해당 부분을 수정할 예정
         */
        window.location.reload();
    } catch (error) {
        console.error("댓글 삭제 실패:", error);
        alert("댓글 삭제에 실패했습니다. 다시 시도해주세요.");
    }
});

/** 댓글 삭제 API 호출 및 데이터 처리 끝 */

/** 게시글 좋아요 API 호출 및 데이터 처리 시작 */

const likeButton = document.querySelector(".post-likes");

likeButton.addEventListener("click", async () => {
    if (isPostLike) {
        PostAPI.unlikePost(postIdFromPath);
        isPostLike = false;
    } else {
        PostAPI.likePost(postIdFromPath);
        isPostLike = true;
    }
    console.log(isPostLike);
    likeButton.innerHTML = `${isPostLike ? "❤️" : "🤍"} 좋아요`;

    /**
     * 현재 게시글 좋아요 API에서 해당 게시글에 대한 업데이트된 좋아요 수를 반환하지 않으므로
     * 좋아요 클릭 후 새로고침을 통해 게시글 상세를 다시 불러오는 방식으로 처리
     * 추후 API에서 업데이트된 좋아요 수를 반환하도록 수정 시, 해당 부분을 수정할 예정
     */
    window.location.reload();
});

/** 게시글 좋아요 API 호출 및 데이터 처리 끝 */