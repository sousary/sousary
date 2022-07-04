const Joi = require('joi');

module.exports = Joi.object({
    email: Joi.string()
    .email({minDomainSegments: 2, tlds: {allow: ['com', 'net', 'fr']}}),
    username: Joi.string()
        .alphanum()
        .min(3)
        .max(30)
        .required(),
    password: Joi.string()
        .pattern(new RegExp('^[a-zA-Z0-9]{3,30}$'))
        .required(),
    repeatPassword: Joi.ref('password')
}).with('password', 'repeatPassword');