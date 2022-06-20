const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true,
    devServer: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'https://renting-buddy-spring.herokuapp.com/',
                ws: true,
                changeOrigin: true
            },
            '/auth' : {
                target: 'https://renting-buddy-spring.herokuapp.com/',
                ws: true,
                changeOrigin: true
            }
        }
    }
})
