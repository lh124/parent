/**
 * CommonDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package io.yfjz.service.jwxplat;

public class CommonDTO  extends io.yfjz.service.jwxplat.BaseDto  implements java.io.Serializable {
    private java.lang.String controlDepartmentSelect;

    private java.lang.String deleteSql;

    private java.lang.String insertSql;

    private java.lang.String queryOneSql;

    private java.lang.String querySql;

    private java.lang.String updateSql;

    public CommonDTO() {
    }

    public CommonDTO(
           java.lang.Integer count,
           java.lang.String[] depa_ids,
           java.lang.String depa_level,
           java.lang.String depa_type,
           java.lang.Integer end,
           java.lang.Integer endPage,
           java.lang.Integer page,
           java.lang.Integer pageCount,
           java.lang.Integer pages,
           java.lang.String queryYear,
           java.lang.Integer start,
           java.lang.Integer startPage,
           java.lang.Integer topCount,
           java.lang.String controlDepartmentSelect,
           java.lang.String deleteSql,
           java.lang.String insertSql,
           java.lang.String queryOneSql,
           java.lang.String querySql,
           java.lang.String updateSql) {
        super(
            count,
            depa_ids,
            depa_level,
            depa_type,
            end,
            endPage,
            page,
            pageCount,
            pages,
            queryYear,
            start,
            startPage,
            topCount);
        this.controlDepartmentSelect = controlDepartmentSelect;
        this.deleteSql = deleteSql;
        this.insertSql = insertSql;
        this.queryOneSql = queryOneSql;
        this.querySql = querySql;
        this.updateSql = updateSql;
    }


    /**
     * Gets the controlDepartmentSelect value for this CommonDTO.
     * 
     * @return controlDepartmentSelect
     */
    public java.lang.String getControlDepartmentSelect() {
        return controlDepartmentSelect;
    }


    /**
     * Sets the controlDepartmentSelect value for this CommonDTO.
     * 
     * @param controlDepartmentSelect
     */
    public void setControlDepartmentSelect(java.lang.String controlDepartmentSelect) {
        this.controlDepartmentSelect = controlDepartmentSelect;
    }


    /**
     * Gets the deleteSql value for this CommonDTO.
     * 
     * @return deleteSql
     */
    public java.lang.String getDeleteSql() {
        return deleteSql;
    }


    /**
     * Sets the deleteSql value for this CommonDTO.
     * 
     * @param deleteSql
     */
    public void setDeleteSql(java.lang.String deleteSql) {
        this.deleteSql = deleteSql;
    }


    /**
     * Gets the insertSql value for this CommonDTO.
     * 
     * @return insertSql
     */
    public java.lang.String getInsertSql() {
        return insertSql;
    }


    /**
     * Sets the insertSql value for this CommonDTO.
     * 
     * @param insertSql
     */
    public void setInsertSql(java.lang.String insertSql) {
        this.insertSql = insertSql;
    }


    /**
     * Gets the queryOneSql value for this CommonDTO.
     * 
     * @return queryOneSql
     */
    public java.lang.String getQueryOneSql() {
        return queryOneSql;
    }


    /**
     * Sets the queryOneSql value for this CommonDTO.
     * 
     * @param queryOneSql
     */
    public void setQueryOneSql(java.lang.String queryOneSql) {
        this.queryOneSql = queryOneSql;
    }


    /**
     * Gets the querySql value for this CommonDTO.
     * 
     * @return querySql
     */
    public java.lang.String getQuerySql() {
        return querySql;
    }


    /**
     * Sets the querySql value for this CommonDTO.
     * 
     * @param querySql
     */
    public void setQuerySql(java.lang.String querySql) {
        this.querySql = querySql;
    }


    /**
     * Gets the updateSql value for this CommonDTO.
     * 
     * @return updateSql
     */
    public java.lang.String getUpdateSql() {
        return updateSql;
    }


    /**
     * Sets the updateSql value for this CommonDTO.
     * 
     * @param updateSql
     */
    public void setUpdateSql(java.lang.String updateSql) {
        this.updateSql = updateSql;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CommonDTO)) return false;
        CommonDTO other = (CommonDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.controlDepartmentSelect==null && other.getControlDepartmentSelect()==null) || 
             (this.controlDepartmentSelect!=null &&
              this.controlDepartmentSelect.equals(other.getControlDepartmentSelect()))) &&
            ((this.deleteSql==null && other.getDeleteSql()==null) || 
             (this.deleteSql!=null &&
              this.deleteSql.equals(other.getDeleteSql()))) &&
            ((this.insertSql==null && other.getInsertSql()==null) || 
             (this.insertSql!=null &&
              this.insertSql.equals(other.getInsertSql()))) &&
            ((this.queryOneSql==null && other.getQueryOneSql()==null) || 
             (this.queryOneSql!=null &&
              this.queryOneSql.equals(other.getQueryOneSql()))) &&
            ((this.querySql==null && other.getQuerySql()==null) || 
             (this.querySql!=null &&
              this.querySql.equals(other.getQuerySql()))) &&
            ((this.updateSql==null && other.getUpdateSql()==null) || 
             (this.updateSql!=null &&
              this.updateSql.equals(other.getUpdateSql())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    @Override
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getControlDepartmentSelect() != null) {
            _hashCode += getControlDepartmentSelect().hashCode();
        }
        if (getDeleteSql() != null) {
            _hashCode += getDeleteSql().hashCode();
        }
        if (getInsertSql() != null) {
            _hashCode += getInsertSql().hashCode();
        }
        if (getQueryOneSql() != null) {
            _hashCode += getQueryOneSql().hashCode();
        }
        if (getQuerySql() != null) {
            _hashCode += getQuerySql().hashCode();
        }
        if (getUpdateSql() != null) {
            _hashCode += getUpdateSql().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommonDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.vaccine.nipm.jwx.com", "CommonDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("controlDepartmentSelect");
        elemField.setXmlName(new javax.xml.namespace.QName("", "controlDepartmentSelect"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deleteSql");
        elemField.setXmlName(new javax.xml.namespace.QName("", "deleteSql"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("insertSql");
        elemField.setXmlName(new javax.xml.namespace.QName("", "insertSql"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryOneSql");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryOneSql"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("querySql");
        elemField.setXmlName(new javax.xml.namespace.QName("", "querySql"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateSql");
        elemField.setXmlName(new javax.xml.namespace.QName("", "updateSql"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
