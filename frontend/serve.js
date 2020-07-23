//https://github.com/parcel-bundler/parcel/issues/55
const Bundler = require("parcel-bundler")
const express = require("express")
const { createProxyMiddleware} = require("http-proxy-middleware")

const app = express()
app.use(createProxyMiddleware("/api", {
    target : "http://localhost:8080"
}))

const bundler = new Bundler("src/index.html", { outDir : "target/dist" })
app.use(bundler.middleware())

app.listen(1234)