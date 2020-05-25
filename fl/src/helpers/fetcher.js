"use strict";

module.exports = class{
  constructor( fetch_ ){
    // #fix добавить возможность определять базовый путь
    this.baseUrl = "";
    this.schemas = {
      json: async response => await response.json(),
      default: async response => await response.text()
    };

    // #fix сделать нормальный способ передавать свой fetch
    if( fetch_ !== undefined )
      this.fetch = fetch_;
  }

  makeUrl( url ){
    if( url[0] === "/" ) return this.baseUrl + url.slice( 1, url.length );

    return this.baseUrl + url;
  }

  makeQuery( options, level ){
    if( options === undefined || options === null ) return "";
    if( typeof options === "string" ) return "?" + options;
    if( typeof options === "object" ){
      if( options.length !== undefined ) return "?" + options.join( "&" );
      if( level === undefined ) return this.makeQuery( options.query, 1 );

      const serialized = [];

      for( let key in options ){
        const field = options[ key ];

        if( typeof field === "object" ){
          if( Array.isArray( field ) )
            serialized.push( `${key}=${field.join( "," )}` );
          else for( let key2 in field ){
            const field2 = field[ key2 ];

            if( typeof field2 === "object" && Array.isArray( field2 ) )
              serialized.push( `${key}[${key2}]=${field2.join( "," )}` );
            else if( field2 === "" )
              serialized.push( `${key}[${key2}]` );
            else
              serialized.push( `${key}[${key2}]=${field2}` );
          }
        }
        else if( field === "" )
          serialized.push( key );
        else
          serialized.push( `${key}=${field}` );
      }

      return "?" + serialized.join( "&" );
    }

    return "";
  }

  async serialize( response, schema ){
    if( this.schemas[ schema ] ) schema = this.schemas[ schema ];
    else schema = this.schemas.default;

    return await schema( response );
  }

  async send( url, body, options, method ){
    const query = this.makeQuery( options );
    let response;

    url = this.makeUrl( url );

    if( typeof options === "object" && options.length === undefined ){
      if( options.query ) delete options.query;
    }
    else options = {};

    options.method = method;

    if( method === "POST" || method === "PUT" ){
      if( options.bodyType === undefined || options.bodyType === "json" ){
        options.headers = {
          "Content-Type": "application/json"
        };
        options.body = JSON.stringify( body );
      }
      else if( options.bodyType === "formData" ){
        const body_ = new FormData();

        for( let key in body ){
          const field = body[ key ];

          if( !field ) continue;

          if( typeof field === "object" && field.length !== undefined )
            for( let elem of field )
              body_.append( key, elem );
          else
            body_.append( key, field );
        }

        options.body = body_;
      }

      delete options.bodyType;
    }

    if( this.fetch ) response = await this.fetch( url + query, options );
    else response = await fetch( url + query, options );

    if( !response.ok ) return response;

    // #fix добавить redirect

    // #fix другой обработчик
    return await this.serialize( response, "json" );
  }

  get( url, options ){
    return this.send( url, null, options, "GET" );
  }

  post( url, body, options ){
    return this.send( url, body, options, "POST" );
  }

  put( url, body, options ){
    return this.send( url, body, options, "PUT" );
  }

  patch( url, body, options ){
    return this.send( url, body, options, "PATCH" );
  }

  async delete( url, options ){
    return await this.send( url, null, options, "DELETE" );
  }
}
