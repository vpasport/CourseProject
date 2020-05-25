"use strict";

export {
  toNumber,
  toInt,
  toFloat,
  toIntArray
};

// ==================== TO NUMBER ====================
function toNumber( el, type ){
  if( type !== "int" && type !== "float" )
    return null;

  if( typeof el === "number" )
    return el;

  if( typeof el !== "string" )
    return null;

  el = el.replace( / +/g, "" );

  if( el === "" )
    return null;

  el = type === "int" ? parseInt( el ) : parseFloat( el );

  if( typeof el !== "number" || isNaN( el ) )
    return null;

  return el;
}

// ==================== TO INT ====================
function toInt( el ){
  return toNumber( el, "int" );
}

// ==================== TO FLOAT ====================
function toFloat( el ){
  return toNumber( el, "float" );
}

// ==================== TO INT ARRAY ====================
function toIntArray( el, splitter ){
  if( Array.isArray( el ) && el.every( el_ => Number.isInteger( el_ ) ) )
    return el;

  let arr;

  if( typeof el === "string" ){
    el = el.replace( / +/g, "" );

    if( el === "" )
      return null;

    if( typeof splitter !== "string" || splitter === "" )
      splitter = ",";

    arr = el.split( splitter );
  } else {
    if( !Array.isArray( el ) )
      return null;

    arr = [ ...el ];
  }

  for( let i = 0; i < arr.length; i++ ){
    arr[i] = toNumber( arr[i], "int" );

    if( arr[i] === null )
      return null;
  }

  return arr;
}
