module.exports = {
    mode: 'jit',
    content: {
        files: ['./src/cljs/**/*.cljs'],
        extract: {
            wtf: (content) => {
                content.match(/[^<>"'.`\s]*[^<>"'.`\s:]/g)
            }
        }
    },
    variants: {},
    plugins: [require('@tailwindcss/forms')]
};
