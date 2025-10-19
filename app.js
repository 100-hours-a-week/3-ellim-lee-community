const express = require('express');
const Path = require('path');
const app = express();
const port = 3000;

app.use(express.static(Path.join(__dirname, 'public')));
app.use('/assets', express.static(Path.join(__dirname, 'assets')));

const postRouter = require('./public/pages/posts/js/postRoutes');
app.use('/posts', postRouter);

const userRouter = require('./public/pages/users/js/userRouter');
app.use('/users', userRouter);

app.listen(port, () => {
    console.log(`Server is running at http://localhost:${port}`);
});