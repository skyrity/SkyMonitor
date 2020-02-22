module.exports = {
  /** 区分打包环境与开发环境

     * process.env.NODE_ENV==='production'  (打包环境)

     * process.env.NODE_ENV==='development' (开发环境)

     * baseUrl: process.env.NODE_ENV==='production'?"https://cdn.didabisai.com/front/":'front/',

     */

  // 基本路径
  // baseUrl 已经被废除，由publicPath代替
  // baseUrl: '/',
  publicPath: process.env.NODE_ENV === 'production' ? './' : '/',

  // 输出文件目录
  outputDir: 'dist',
  assetsDir: 'statics',

  devServer: {
    // open: process.platform === 'darwin',

    // host: '127.0.0.1',

    port: 8888,

    // https: false,

    // hotOnly: false,

    proxy: {
      //   '^/shop/api': {
      //     target: 'http://127.0.0.1:8080',
      //     changeOrigin: true
      //   },
      //   '^/shop/foo': {
      //     target: 'http://127.0.0.1:8080'
      //   }
    },

    before: app => {}
  },

  // 第三方插件配置

  pluginOptions: {
    // ...
  },

  css: {
    extract: false
  }
}
