// models/StarbucksStore.js

import { DataTypes, Model } from "sequelize";
import sequelize from "../db.js";

class StarbucksStore extends Model {}

StarbucksStore.init(
  {
    storeNumber: {
      type: DataTypes.TEXT,
      primaryKey: true,
      field: "STORE_NUMBER"
    },
    storeName: {
      type: DataTypes.TEXT,
      field: "STORE_NAME"
    },
    streetAddress: {
      type: DataTypes.TEXT,
      field: "STREET_ADDRESS"
    },
    city: {
      type: DataTypes.TEXT,
      field: "CITY"
    },
    province: {
      type: DataTypes.TEXT,
      field: "PROVINCE"
    },
    country: {
      type: DataTypes.TEXT,
      field: "COUNTRY"
    },
    postcode: {
      type: DataTypes.TEXT,
      field: "POSTCODE"
    },
    longitude: {
      type: DataTypes.REAL,
      field: "LONGITUDE"
    },
    latitude: {
      type: DataTypes.REAL,
      field: "LATITUDE"
    }
  },
  {
    sequelize,
    modelName: "StarbucksStore",
    tableName: "STARBUCKS_DIRECTORY",
    timestamps: false
  }
);

export default StarbucksStore;
