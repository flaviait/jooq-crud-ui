var express = require("express");
var httpProxy = require('http-proxy');
var chalk = require("chalk");
var livereload = require("livereload");

var target = "http://localhost:8080";
var proxy = httpProxy.createProxyServer({target: target});
app = express();

var staticFiles = "src/main/resources/static";
app.use(express.static(staticFiles));
app.use((req, res) => {
  console.log(`Proxying: ${chalk.yellow(`${req.method} ${target}${req.path}`)}`);
  proxy.web(req, res);
});

app.listen(8082);

livereload.createServer().watch(staticFiles);