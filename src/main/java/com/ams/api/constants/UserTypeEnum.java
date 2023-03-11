package com.ams.api.constants;

public enum UserTypeEnum {
	  ADMIN(1),
      MERCHANT(2),
      OPERATION(3);
      
      private final int type;

      UserTypeEnum(int type){
    	  this.type = type;
      }
      public int getType() {
          return this.type;
      }

}
