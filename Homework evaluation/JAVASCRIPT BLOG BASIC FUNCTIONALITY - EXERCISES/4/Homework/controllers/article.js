const Article = require('../models').Article;

module.exports = {
    createGet:(req, res) => {
        res.render('article/create');
    },

    createPost:(req,res)=> {
        let articleArgs = req.body;
        let errorMsg = '';

        if (!req.isAuthenticated()) {
            errorMsg = 'you should be logged in to make articles!'
        } else if (!articleArgs.title) {
            errorMsg = 'Invalid title!';
        } else if (!articleArgs.content) {
            errorMsg = 'Invalid content';
        }

        if (errorMsg) {
            res.render('article/create', {error: errorMsg});
            return;
        }
        articleArgs.authorId = req.user.id;

        Article.create(articleArgs).then(article => {
            res.redirect('/');
        }).catch(err => {
            console.log(err.message);
            res.render('article/create', {error: err.message});
        });
    },

    detailsGet:(req, res) =>{
        let id = req.params.id;
        Article.findById(id).then(article=>{
            res.render('article/details', article.dataValues)
        })
    }


};