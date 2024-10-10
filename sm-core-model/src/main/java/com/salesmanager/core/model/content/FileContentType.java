/**
 * 
 */
package com.salesmanager.core.model.content;

/**
 * Enum defining type of static content.
 * Currently following type of static content can be store and managed within 
 * Shopizer CMS system
 * <pre>
 * 1. Static content like JS, CSS file etc
 * 2. Digital Data (audio,video)
 * </pre>
 * 
 * StaticContentType will be used to distinguish between Digital data and other type of static data
 * stored with in the system.
 * 
 * @author Umesh Awasthi
 * @since 1.2
 *
 */
public enum FileContentType
{
  STATIC_FILE, IMAGE, LOGO, PRODUCT, PRODUCTLG, PROPERTY, VARIANT, CLEARANCE_DOCUMENT,
  MANUFACTURER, PRODUCT_DIGITAL, API_IMAGE, API_FILE, VIDEO, CERTIFICATION_INFORMATION,
  PATENT_INFORMATION, Banner, LIBRARY_IMAGE, STORE_IMAGE, PRODUCT_REVIEW_IMAGE, PRODUCT_QNA_IMAGE,
  ADDITIONAL_SERVICES_QC,

  /**
   * 提单
   */
  BL,
  /**
   * 保险
   */
  INSURANCE,

  /**
   * 出口报关单
   */
  EXPORT_DECLARATION,
  /**
   * 进口报关单
   */
  CUSTOM_CLAIM,

  /**
   * 报关费用结算单
   */
  DECLARATION_FEE_SETTLEMENT,
  

  }
