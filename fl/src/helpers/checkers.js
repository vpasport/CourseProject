"use strict";

export {
  isPositiveInt,
  isNotEmptyString,
  isPlainObject,
  isNotEmptyPlainObject,
  isNotEmptyIntArray,
  isNotEmptyPositiveIntArray
};

/* ==================== NUMBER ==================== */
function isPositiveInt( obj ){
  return Number.isInteger( obj ) && obj > 0;
}

/* ==================== STRING ==================== */
function isNotEmptyString( obj ){
  return typeof obj === "string" && obj.length > 0;
}

/* ==================== PLAIN OBJECT ==================== */
function isPlainObject( obj ){
  return obj !== null && typeof obj === "object" && !Array.isArray( obj );
}

function isNotEmptyPlainObject( obj ){
  return obj !== null && typeof obj === "object" && !Array.isArray( obj ) && Object.keys( obj ).length > 0;
}

/* ==================== ARRAY ==================== */
function isNotEmptyIntArray( obj ){
  return Array.isArray( obj ) && obj.length > 0 && obj.every( el => Number.isInteger( el ) );
}

function isNotEmptyPositiveIntArray( obj ){
  return Array.isArray( obj ) && obj.length > 0 && obj.every( el => Number.isInteger( el ) && el > 0 );
}
