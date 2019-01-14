/**
 * BaseDto.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package io.yfjz.service.jwxplat;

public class BaseDto  extends io.yfjz.service.jwxplat.JfaDTO  implements java.io.Serializable {
    private java.lang.Integer count;

    private java.lang.String[] depa_ids;

    private java.lang.String depa_level;

    private java.lang.String depa_type;

    private java.lang.Integer end;

    private java.lang.Integer endPage;

    private java.lang.Integer page;

    private java.lang.Integer pageCount;

    private java.lang.Integer pages;

    private java.lang.String queryYear;

    private java.lang.Integer start;

    private java.lang.Integer startPage;

    private java.lang.Integer topCount;

    public BaseDto() {
    }

    public BaseDto(
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
           java.lang.Integer topCount) {
        this.count = count;
        this.depa_ids = depa_ids;
        this.depa_level = depa_level;
        this.depa_type = depa_type;
        this.end = end;
        this.endPage = endPage;
        this.page = page;
        this.pageCount = pageCount;
        this.pages = pages;
        this.queryYear = queryYear;
        this.start = start;
        this.startPage = startPage;
        this.topCount = topCount;
    }


    /**
     * Gets the count value for this BaseDto.
     * 
     * @return count
     */
    public java.lang.Integer getCount() {
        return count;
    }


    /**
     * Sets the count value for this BaseDto.
     * 
     * @param count
     */
    public void setCount(java.lang.Integer count) {
        this.count = count;
    }


    /**
     * Gets the depa_ids value for this BaseDto.
     * 
     * @return depa_ids
     */
    public java.lang.String[] getDepa_ids() {
        return depa_ids;
    }


    /**
     * Sets the depa_ids value for this BaseDto.
     * 
     * @param depa_ids
     */
    public void setDepa_ids(java.lang.String[] depa_ids) {
        this.depa_ids = depa_ids;
    }


    /**
     * Gets the depa_level value for this BaseDto.
     * 
     * @return depa_level
     */
    public java.lang.String getDepa_level() {
        return depa_level;
    }


    /**
     * Sets the depa_level value for this BaseDto.
     * 
     * @param depa_level
     */
    public void setDepa_level(java.lang.String depa_level) {
        this.depa_level = depa_level;
    }


    /**
     * Gets the depa_type value for this BaseDto.
     * 
     * @return depa_type
     */
    public java.lang.String getDepa_type() {
        return depa_type;
    }


    /**
     * Sets the depa_type value for this BaseDto.
     * 
     * @param depa_type
     */
    public void setDepa_type(java.lang.String depa_type) {
        this.depa_type = depa_type;
    }


    /**
     * Gets the end value for this BaseDto.
     * 
     * @return end
     */
    public java.lang.Integer getEnd() {
        return end;
    }


    /**
     * Sets the end value for this BaseDto.
     * 
     * @param end
     */
    public void setEnd(java.lang.Integer end) {
        this.end = end;
    }


    /**
     * Gets the endPage value for this BaseDto.
     * 
     * @return endPage
     */
    public java.lang.Integer getEndPage() {
        return endPage;
    }


    /**
     * Sets the endPage value for this BaseDto.
     * 
     * @param endPage
     */
    public void setEndPage(java.lang.Integer endPage) {
        this.endPage = endPage;
    }


    /**
     * Gets the page value for this BaseDto.
     * 
     * @return page
     */
    public java.lang.Integer getPage() {
        return page;
    }


    /**
     * Sets the page value for this BaseDto.
     * 
     * @param page
     */
    public void setPage(java.lang.Integer page) {
        this.page = page;
    }


    /**
     * Gets the pageCount value for this BaseDto.
     * 
     * @return pageCount
     */
    public java.lang.Integer getPageCount() {
        return pageCount;
    }


    /**
     * Sets the pageCount value for this BaseDto.
     * 
     * @param pageCount
     */
    public void setPageCount(java.lang.Integer pageCount) {
        this.pageCount = pageCount;
    }


    /**
     * Gets the pages value for this BaseDto.
     * 
     * @return pages
     */
    public java.lang.Integer getPages() {
        return pages;
    }


    /**
     * Sets the pages value for this BaseDto.
     * 
     * @param pages
     */
    public void setPages(java.lang.Integer pages) {
        this.pages = pages;
    }


    /**
     * Gets the queryYear value for this BaseDto.
     * 
     * @return queryYear
     */
    public java.lang.String getQueryYear() {
        return queryYear;
    }


    /**
     * Sets the queryYear value for this BaseDto.
     * 
     * @param queryYear
     */
    public void setQueryYear(java.lang.String queryYear) {
        this.queryYear = queryYear;
    }


    /**
     * Gets the start value for this BaseDto.
     * 
     * @return start
     */
    public java.lang.Integer getStart() {
        return start;
    }


    /**
     * Sets the start value for this BaseDto.
     * 
     * @param start
     */
    public void setStart(java.lang.Integer start) {
        this.start = start;
    }


    /**
     * Gets the startPage value for this BaseDto.
     * 
     * @return startPage
     */
    public java.lang.Integer getStartPage() {
        return startPage;
    }


    /**
     * Sets the startPage value for this BaseDto.
     * 
     * @param startPage
     */
    public void setStartPage(java.lang.Integer startPage) {
        this.startPage = startPage;
    }


    /**
     * Gets the topCount value for this BaseDto.
     * 
     * @return topCount
     */
    public java.lang.Integer getTopCount() {
        return topCount;
    }


    /**
     * Sets the topCount value for this BaseDto.
     * 
     * @param topCount
     */
    public void setTopCount(java.lang.Integer topCount) {
        this.topCount = topCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BaseDto)) return false;
        BaseDto other = (BaseDto) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.count==null && other.getCount()==null) || 
             (this.count!=null &&
              this.count.equals(other.getCount()))) &&
            ((this.depa_ids==null && other.getDepa_ids()==null) || 
             (this.depa_ids!=null &&
              java.util.Arrays.equals(this.depa_ids, other.getDepa_ids()))) &&
            ((this.depa_level==null && other.getDepa_level()==null) || 
             (this.depa_level!=null &&
              this.depa_level.equals(other.getDepa_level()))) &&
            ((this.depa_type==null && other.getDepa_type()==null) || 
             (this.depa_type!=null &&
              this.depa_type.equals(other.getDepa_type()))) &&
            ((this.end==null && other.getEnd()==null) || 
             (this.end!=null &&
              this.end.equals(other.getEnd()))) &&
            ((this.endPage==null && other.getEndPage()==null) || 
             (this.endPage!=null &&
              this.endPage.equals(other.getEndPage()))) &&
            ((this.page==null && other.getPage()==null) || 
             (this.page!=null &&
              this.page.equals(other.getPage()))) &&
            ((this.pageCount==null && other.getPageCount()==null) || 
             (this.pageCount!=null &&
              this.pageCount.equals(other.getPageCount()))) &&
            ((this.pages==null && other.getPages()==null) || 
             (this.pages!=null &&
              this.pages.equals(other.getPages()))) &&
            ((this.queryYear==null && other.getQueryYear()==null) || 
             (this.queryYear!=null &&
              this.queryYear.equals(other.getQueryYear()))) &&
            ((this.start==null && other.getStart()==null) || 
             (this.start!=null &&
              this.start.equals(other.getStart()))) &&
            ((this.startPage==null && other.getStartPage()==null) || 
             (this.startPage!=null &&
              this.startPage.equals(other.getStartPage()))) &&
            ((this.topCount==null && other.getTopCount()==null) || 
             (this.topCount!=null &&
              this.topCount.equals(other.getTopCount())));
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
        if (getCount() != null) {
            _hashCode += getCount().hashCode();
        }
        if (getDepa_ids() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDepa_ids());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDepa_ids(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDepa_level() != null) {
            _hashCode += getDepa_level().hashCode();
        }
        if (getDepa_type() != null) {
            _hashCode += getDepa_type().hashCode();
        }
        if (getEnd() != null) {
            _hashCode += getEnd().hashCode();
        }
        if (getEndPage() != null) {
            _hashCode += getEndPage().hashCode();
        }
        if (getPage() != null) {
            _hashCode += getPage().hashCode();
        }
        if (getPageCount() != null) {
            _hashCode += getPageCount().hashCode();
        }
        if (getPages() != null) {
            _hashCode += getPages().hashCode();
        }
        if (getQueryYear() != null) {
            _hashCode += getQueryYear().hashCode();
        }
        if (getStart() != null) {
            _hashCode += getStart().hashCode();
        }
        if (getStartPage() != null) {
            _hashCode += getStartPage().hashCode();
        }
        if (getTopCount() != null) {
            _hashCode += getTopCount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BaseDto.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.vaccine.nipm.jwx.com", "BaseDto"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("count");
        elemField.setXmlName(new javax.xml.namespace.QName("", "count"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_ids");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_ids"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_level");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_level"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("depa_type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "depa_type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("end");
        elemField.setXmlName(new javax.xml.namespace.QName("", "end"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endPage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("page");
        elemField.setXmlName(new javax.xml.namespace.QName("", "page"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pages");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryYear");
        elemField.setXmlName(new javax.xml.namespace.QName("", "queryYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("start");
        elemField.setXmlName(new javax.xml.namespace.QName("", "start"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startPage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "topCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
