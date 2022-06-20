const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        port: 3000,
        proxy: {
            '/api/': { target: 'https://renting-buddy-spring.herokuapp.com/api', pathRewrite: { '^/api/': '' } }
        }
    }
})

