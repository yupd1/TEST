package com.wondertek.mobilevideo.core.util.vo;

import java.io.Serializable;

/*
 * prdContId: 接入系统的资源id:dataType为1时为节目id(prdContId);dataType为2,3,4时为voms内容id(contId)
 * pContId： 接入系统的资源内容Id:dataType为1时为内容id(PContId);dataType为2和4时为channelId;dataType为3时为sequence
 * cpId：接入系统的资源cpId;
 * dataType：接入系统的数据源类型:1表示移动点播类型;2表示移动直播类型;3表示自运营点播类型;4表示自运营直播点播类型
 */
public class ProductCont implements Serializable {
	private static final long serialVersionUID = 2623247362641996078L;
	public static final String FORM_TYPE_0 = "0"; //未知
	public static final String FORM_TYPE_6 = "6"; //剧集壳
	public static final String FORM_TYPE_7 = "7"; //剧集的单集
	public static final String FORM_TYPE_8 = "8"; //非剧集
	
	private Long prdContId; 		// 产品ID(注:dataType为1时为节目id;dataType为2时为节目id;dataType为3时为id)
	private String PContId;			//华为或思华的内容Id(注:dataType为1时为华为或思华的内容Id;dataType为3时为内容id)
	private Long dataType; 			//数据类型
	private String formType;		//媒资类型,6：剧集壳；7：剧集的单集；8：非剧集 ； 0：未知
	private String name;			//内容名称
	private String cpId;			//合作伙伴编号
	private String CDuration;		//播放时长
	private String displayName;		//一级分类名称
	private String pricePakDesc;	//计费节目包描述(upc-admin)
	
	public ProductCont() {
		super();
	}

	//点播节目构造函数
	public ProductCont(Long prdContId, Long dataType, String pContId,
			String formType, String name, String cpId, String cDuration,
			String displayName) {
		super();
		this.prdContId = prdContId;
		this.PContId = pContId;
		this.dataType = dataType;
		this.formType = formType;
		this.name = name;
		this.cpId = cpId;
		this.CDuration = cDuration;
		this.displayName = displayName;
	}
	
	//直播节目构造函数
	public ProductCont(Long id, Long dataType, String channelId, String name) {
		super();
		this.prdContId = id;
		this.dataType = dataType;
		this.PContId = channelId;
		this.formType = ProductCont.FORM_TYPE_0;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCont pojo = (ProductCont) o;
        if (prdContId != null ? !prdContId.equals(pojo.prdContId) : pojo.prdContId != null) return false;
        if (PContId != null ? !PContId.equals(pojo.PContId) : pojo.PContId != null) return false;
        if (dataType != null ? !dataType.equals(pojo.dataType) : pojo.dataType != null) return false;
        return true;
    }

	@Override
	public int hashCode() {
        int result = 0;
        result = 31 * result + (prdContId != null ? prdContId.hashCode() : 0);
        result = 31 * result + (PContId != null ? PContId.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        return result;
    }

    @Override
	public String toString() {
        StringBuffer sb = new StringBuffer(getClass().getSimpleName());
        sb.append(" [");
        sb.append("prdContId").append("='").append(getPrdContId()).append("', ");
        sb.append("PContId").append("='").append(getPContId()).append("', ");
        sb.append("dataType").append("='").append(getDataType()).append("'");
        sb.append("]");
        return sb.toString();
    }
    
	public Long getPrdContId() {
		return prdContId;
	}

	public void setPrdContId(Long prdContId) {
		this.prdContId = prdContId;
	}

	public String getPContId() {
		return PContId;
	}

	public void setPContId(String pContId) {
		PContId = pContId;
	}

	public Long getDataType() {
		return dataType;
	}

	public void setDataType(Long dataType) {
		this.dataType = dataType;
	}

	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpId() {
		return cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getCDuration() {
		return CDuration;
	}

	public void setCDuration(String cDuration) {
		CDuration = cDuration;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getPricePakDesc() {
		return pricePakDesc;
	}

	public void setPricePakDesc(String pricePakDesc) {
		this.pricePakDesc = pricePakDesc;
	}
}