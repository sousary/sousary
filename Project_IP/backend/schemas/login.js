const Joi = require('joi');

module.exports = Joi.object({
    email: Joi.string()
        .email({minDomainSegments: 2, tlds: {allow: ['com', 'net', 'fr']}}),
    password: Joi.string()
        .pattern(new RegExp('^[a-zA-Z0-9]{3,30}$'))
})