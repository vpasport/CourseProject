"use strict";

// Imports
import sirv from "sirv";
import express from "express";
import compression from "compression";
import * as sapper from "@sapper/server";
import helmet from "helmet";
import bodyParser from "body-parser";

// Configs
import "./configs/env";

// Consts
const { PORT, NODE_ENV } = process.env;
const dev = NODE_ENV === "development";

const server = express();

// Settings
server.use(
  helmet(),
  bodyParser.json(),
  bodyParser.urlencoded( {
    extended: true
  } ),
	compression( {
    threshold: 0
  } )
);

// Some upgrade
server.use( ( req, res, next ) => {
  //

  next();
} );

// Run
server
  .use(
    sirv( "static", {
      dev
    } ),
    sapper.middleware()
  )
  .listen( PORT, err => {
  	if( err ) console.log( "error", err );
  } );
