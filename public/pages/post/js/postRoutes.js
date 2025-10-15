const express = require('express');
const path = require('path');
const router = express.Router();

router.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'post-list.html'));
});

router.get('/create/new', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'post-create.html'));
});

router.get('/edit/:id', (req, res) => {
    res.sendFile(path.join(__dirname, '..', 'post-edit.html'));
});

router.get('/:id', (req, res, next) => {
    const id = req.params.id;
    if (!/^\d+$/.test(id)) return next();
    res.sendFile(path.join(__dirname, '..', 'post-detail.html'));
}); // 정규식으로 숫자만 허용

module.exports = router;