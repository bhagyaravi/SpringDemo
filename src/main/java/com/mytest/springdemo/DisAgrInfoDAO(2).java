package jp.co.alico.cusext.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.alico.cusext.common.LoggerUtil;
import jp.co.alico.cusext.common.Constant;
import jp.co.alico.cusext.form.DisAgrEntryForm;
import jp.co.alico.cusext.vo.DisAgrmntInfoVO;

/**
 * <p>�V�X�e�����F CUSEXT</p>
 * <p>�Ɓ@���@���F �s�����񑀍�N���X</p>
 * <p>�T�@�@�@�v�F �s������̎擾��o�^��C�����s���B</p>
 * <p>��@���@���F 2010/10/22</p>
 *
 * @version 1.0.0
 * @author �V�X�e���Z�� JH.Lee
 */
public class DisAgrInfoDAO extends DAOBase {
    // Log�C���X�^���X�̐���
    private LoggerUtil logger = new LoggerUtil( this.getClass().getName() );

    /**
     * �s������ꗗ���擾����B
     * @param actId �s��������擾���銈������ID
     * @return List<DisAgrmntInfoVO> �s������������X�g
     * @throws Exception ��O����
     * @throws SQLException ��O����
     */
    public List<DisAgrmntInfoVO> getDisAgrInfoList(String actId) throws Exception, SQLException {
        return getDisAgrInfoList(actId, true);
    }

    /**
     * �s������ꗗ���擾����B
     * @param actId �s��������擾���銈������ID
     * @param isBranch true - �x�Ѓ}Leads�Afalse - AG�{��Leads
     * @return List<DisAgrmntInfoVO> �s������������X�g
     * @throws Exception ��O����
     * @throws SQLException ��O����
     */
    public List<DisAgrmntInfoVO> getDisAgrInfoList(String actId, boolean isBranch) throws Exception, SQLException {
        if(logger.isDebugEnabled()) {
            logger.methodLog("getDisAgrInfoList", new Object[]{actId}, Constant.METHOD_START);
        }

        // DB�R�l�N�V�����p
        Connection conn = null;
        // SQL�����s�p
        PreparedStatement pstmt = null;
        // SQL���s���ʎ擾�p
        ResultSet  rset = null;
        // �����񃊃X�g
        List<DisAgrmntInfoVO> disAgrInfoList = new ArrayList<DisAgrmntInfoVO>();
        // ���sSQL��
        String strSql = null;
        // ����������I�u�W�F�N�g
        DisAgrmntInfoVO disAgrInfoVO = null;

        try {
            conn = getConnection();

            strSql = getDisAgrInfoSql(false, isBranch);

            // ���s����SQL�������O�ɏo��
            logger.printSql( strSql );

            // SQL���ݒ�
            pstmt = conn.prepareStatement(strSql);

            pstmt.setString(1, actId);

            // SQL�����s
            rset = pstmt.executeQuery();

            // �Y���f�[�^���������ꍇ�̓��X�g�ɐݒ�
            while (rset.next()) {
                disAgrInfoVO = new DisAgrmntInfoVO();
                disAgrInfoVO.setDisAgrmntNo(rset.getString("AGRMNT_NO"));                           // �s����No
                disAgrInfoVO.setCompeAterCmpny1(rset.getString("COMPE_ATER_CMPNY1"));               // ��������1
                disAgrInfoVO.setCompeAterCmpny1Prdct(rset.getString("COMPE_ATER_CMPNY1_PRDCT"));    // ��������1���i
                disAgrInfoVO.setMemo(rset.getString("MEMO"));                                       // ���̑�����
                disAgrInfoVO.setEntryDt(rset.getString("ENTRY_DT"));                                // �o�^����
                disAgrInfoVO.setUpdateDt(rset.getString("UPDATE_DT"));                              // �X�V����
                disAgrInfoList.add(disAgrInfoVO);
            }

        } catch( SQLException sqle ) {
            logger.error("E0002" , sqle );
            throw sqle;
        } catch(Exception e) {
            logger.error("E0003" ,e );
            throw e;
        } finally {
            try {
                close( conn, pstmt, rset );
            } finally {
                rset = null;
                pstmt = null;
                conn = null;
            }
        }
        if(logger.isDebugEnabled()) {
            logger.methodLog("getDisAgrInfoList", new Object[]{disAgrInfoList}, Constant.METHOD_END);
        }
        return disAgrInfoList;
    }

    /**
     * �s������̏ڍׂ��擾����B
     * @param disAgrmntNo �s����No
     * @return DisAgrmntInfoVO �s����ڍ׏������I�u�W�F�N�g
     * @throws Exception ��O����
     * @throws SQLException ��O����
     */
    public DisAgrmntInfoVO getDisAgrDetailInfo(String disAgrmntNo) throws Exception, SQLException {
        return getDisAgrDetailInfo(disAgrmntNo, true);
    }

    /**
     * �s������̏ڍׂ��擾����B
     * @param disAgrmntNo �s����No
     * @param isBranch true - �x�Ѓ}Leads�Afalse - AG�{��Leads
     * @return DisAgrmntInfoVO �s����ڍ׏������I�u�W�F�N�g
     * @throws Exception ��O����
     * @throws SQLException ��O����
     */
    public DisAgrmntInfoVO getDisAgrDetailInfo(String disAgrmntNo, boolean isBranch) throws Exception, SQLException {
        if(logger.isDebugEnabled()) {
            logger.methodLog("getDisAgrDetailInfo", Constant.METHOD_START);
        }

        // DB�R�l�N�V�����p
        Connection conn = null;
        // SQL�����s�p
        PreparedStatement pstmt = null;
        // SQL���s���ʎ擾�p
        ResultSet  rset = null;
        // ���sSQL��
        String strSql = null;
        // �s����������I�u�W�F�N�g
        DisAgrmntInfoVO disAgrInfoVO = null;

        try {
            conn = getConnection();

            strSql = getDisAgrInfoSql(true, isBranch);

            // ���s����SQL�������O�ɏo��
            logger.printSql( strSql );

            // SQL���ݒ�
            pstmt = conn.prepareStatement(strSql);
            pstmt.setString(1, disAgrmntNo);

            // SQL�����s
            rset = pstmt.executeQuery();

            // �Y���f�[�^���������ꍇ�̓��X�g�ɐݒ�
            if (rset.next()) {
                disAgrInfoVO = new DisAgrmntInfoVO();
                disAgrInfoVO.setDisAgrmntNo(rset.getString("AGRMNT_NO"));                           // �s����No
                disAgrInfoVO.setActSubject(rset.getString("ACT_SUBJECT"));                          // ��������
                disAgrInfoVO.setHouseholdId(rset.getString("HOUSEHOLD_ID"));                        // �֘A�ڋq�h�c
                disAgrInfoVO.setTelNg(rset.getString("TEL_NG"));                                    // �A�������Ȃ��E�ʒk���ł��Ȃ�
                disAgrInfoVO.setFrmAcquaintanceApps(rset.getString("FRM_ACQUAINTANCE_APPS"));       // ����W�l�������͒ʔ̂��_��
                disAgrInfoVO.setUndertakeNg(rset.getString("UNDERTAKE_NG"));                        // ���󂪕s�\�ł���
                disAgrInfoVO.setCnfdncNg(rset.getString("CNFDNC_NG"));                              // �M�p���Ȃ�
                disAgrInfoVO.setMemo(rset.getString("MEMO"));                                       // ���̑�����
                disAgrInfoVO.setCompeAterCmpny1(rset.getString("COMPE_ATER_CMPNY1"));               // ��������1
                disAgrInfoVO.setCompeAterCmpny1Prdct(rset.getString("COMPE_ATER_CMPNY1_PRDCT"));    // ��������1���i
                disAgrInfoVO.setCompeAterCmpny2(rset.getString("COMPE_ATER_CMPNY2"));               // ��������2
                disAgrInfoVO.setCompeAterCmpny2Prdct(rset.getString("COMPE_ATER_CMPNY2_PRDCT"));    // ��������2���i
                disAgrInfoVO.setCompeAterCmpny3(rset.getString("COMPE_ATER_CMPNY3"));               // ��������3
                disAgrInfoVO.setCompeAterCmpny3Prdct(rset.getString("COMPE_ATER_CMPNY3_PRDCT"));    // ��������3���i
                disAgrInfoVO.setEntryPgId(rset.getString("ENTRY_PG_ID"));                           // �o�^�v���O����ID
                disAgrInfoVO.setEntryDt(rset.getString("ENTRY_DT"));                                // �o�^����
                disAgrInfoVO.setEntryBy(rset.getString("ENTRY_BY"));                                // �o�^��ID
                disAgrInfoVO.setUpdatePgId(rset.getString("UPDATE_PG_ID"));                         // �X�V�v���O����ID
                disAgrInfoVO.setUpdateDt(rset.getString("UPDATE_DT"));                              // �X�V����
                disAgrInfoVO.setUpdateBy(rset.getString("UPDATE_BY"));                              // �X�V��ID
            }

        } catch( SQLException sqle ) {
            logger.error("E0002" , sqle );
            throw sqle;
        } catch(Exception e) {
            logger.error("E0003" ,e );
            throw e;
        } finally {
            try {
                close( conn, pstmt, rset );
            } finally {
                rset = null;
                pstmt = null;
                conn = null;
            }
        }
        if(logger.isDebugEnabled()) {
            logger.methodLog("getDisAgrDetailInfo", new Object[]{disAgrInfoVO}, Constant.METHOD_END);
        }
        return disAgrInfoVO;
    }

    /**
     * �s�������o�^����B
     * @param disAgrEntryForm �o�^����������I�u�W�F�N�g
     * @param opeId �o�^�҂�OpeId
     * @return �o�^�����s������
     * @throws Exception ��O����
     * @throws SQLException ��O����
     */
    public synchronized DisAgrmntInfoVO insertDisAgrInfo(DisAgrEntryForm disAgrEntryForm, String opeId) throws Exception, SQLException {
        if(logger.isDebugEnabled()) {
            logger.methodLog("insertDisAgrInfo", new Object[]{disAgrEntryForm, opeId}, Constant.METHOD_START);
        }

        // DB�R�l�N�V�����p
        Connection conn = null;
        // SQL�����s�p
        PreparedStatement pstmt = null;
        // SQL���s���ʎ擾�p
        ResultSet  rset = null;
        // ���sSQL��
        StringBuffer strSql = new StringBuffer();
        // �o�^�����s����No
        String disAgrNo = null;

        try {
            conn = getConnection();

            // �L�[���擾
            pstmt = conn.prepareStatement("select 'EN' || " + Constant.SEQ_SCHEMA + ".SEQ_MST_CUS_AGRMNT_NO_EXT.NEXTVAL from dual");
            rset = pstmt.executeQuery();
            if(rset.next()) {
                disAgrNo = rset.getString(1);
            }
            pstmt.close();
            rset.close();

            /** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD Begin */
            //strSql.append("insert into ").append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_EXT (");
            strSql.append("insert into ").append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_DT (");
            /** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD End */
            strSql.append("  SFDC_ID");                                                 // ����E�s�����L�[
            strSql.append("  ,AGRMNT_NO");                                              // �s����No
            strSql.append("  ,ACT_SFDC_ID");                                            // ��������ID
            strSql.append("  ,AGRMNT_NAGRMNT");                                         // ����/�s����
            strSql.append("  ,ACT_SUBJECT");                                            // ��������
            strSql.append("  ,HOUSEHOLD_ID");                                           // �֘A�ڋq�h�c
            strSql.append("  ,TEL_NG");                                                 // �A�������Ȃ��E�ʒk���ł��Ȃ�
            strSql.append("  ,CNFDNC_NG");                                              // ���̑�
            strSql.append("  ,FRM_ACQUAINTANCE_APPS");                                  // ����W�l�������͒ʔ̂��_��
            strSql.append("  ,UNDERTAKE_NG");                                           // ���󂪕s�\�ł���
            strSql.append("  ,MEMO");                                                   // ���̑�����
            strSql.append("  ,COMPE_ATER_CMPNY1");                                      // ��������1
            strSql.append("  ,COMPE_ATER_CMPNY1_PRDCT");                                // ��������1���i
            strSql.append("  ,COMPE_ATER_CMPNY2");                                      // ��������2
            strSql.append("  ,COMPE_ATER_CMPNY2_PRDCT");                                // ��������2���i
            strSql.append("  ,COMPE_ATER_CMPNY3");                                      // ��������3
            strSql.append("  ,COMPE_ATER_CMPNY3_PRDCT");                                // ��������3���i
            strSql.append("  ,DEL_FLG");                                                // �폜�t���O
            strSql.append("  ,ENTRY_PG_ID");                                            // �o�^�v���O����ID
            strSql.append("  ,ENTRY_DT");                                               // �o�^����
            strSql.append("  ,ENTRY_BY");                                               // �o�^��ID
            strSql.append("  ,UPDATE_PG_ID");                                           // �X�V�v���O����ID
            strSql.append("  ,UPDATE_DT");                                              // �X�V����
            strSql.append("  ,UPDATE_BY");                                              // �X�V��ID
            strSql.append("  ) values (");
            strSql.append("  'EXT_AGR_' || ").append(Constant.SEQ_SCHEMA).append(".SEQ_MST_CUS_AGRMNT_ID_EXT.NEXTVAL");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,'�s����'");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,?");
            strSql.append("  ,'FALSE'");
            strSql.append("  ,'CUSEXT', SYSDATE, ?");
            strSql.append("  ,'CUSEXT', SYSDATE, ?");
            strSql.append("  )");

            // ���s����SQL�������O�ɏo��
            logger.printSql( strSql.toString() );

            // SQL���ݒ�
            pstmt = conn.prepareStatement(strSql.toString());
            pstmt.setString(1, disAgrNo);
            pstmt.setString(2, disAgrEntryForm.getActId());
            pstmt.setString(3, disAgrEntryForm.getActSubject());
            pstmt.setString(4, disAgrEntryForm.getHouseholdId());
            pstmt.setString(5, disAgrEntryForm.getTelNg());
            pstmt.setString(6, disAgrEntryForm.getCnfdncNg());
            pstmt.setString(7, disAgrEntryForm.getFrmAcquaintanceApps());
            pstmt.setString(8, disAgrEntryForm.getUndertakeNg());
            pstmt.setString(9, disAgrEntryForm.getMemo());
            pstmt.setString(10, disAgrEntryForm.getCompeAterCmpny1());
            pstmt.setString(11, disAgrEntryForm.getCompeAterCmpny1Prdct());
            pstmt.setString(12, disAgrEntryForm.getCompeAterCmpny2());
            pstmt.setString(13, disAgrEntryForm.getCompeAterCmpny2Prdct());
            pstmt.setString(14, disAgrEntryForm.getCompeAterCmpny3());
            pstmt.setString(15, disAgrEntryForm.getCompeAterCmpny3Prdct());
            pstmt.setString(16, opeId);
            pstmt.setString(17, opeId);

            // �s������o�^
            pstmt.executeUpdate();

        } catch( SQLException sqle ) {
            logger.error("E0002" , sqle );
            throw sqle;
        } catch(Exception e) {
            logger.error("E0003" ,e );
            throw e;
        } finally {
            try {
                close( conn, pstmt, null);
            } finally {
                pstmt = null;
                conn = null;
            }
        }
        if(logger.isDebugEnabled()) {
            logger.methodLog("insertDisAgrInfo", Constant.METHOD_END);
        }
        return getDisAgrDetailInfo(disAgrNo);
    }

    /**
     * �s��������X�V����B
     * @param disAgrEntryForm �X�V����������I�u�W�F�N�g
     * @param opeId �X�V�҂�OpeId
     * @return �o�^�����s������
     * @throws Exception ��O����
     * @throws SQLException ��O����
     */
    public synchronized DisAgrmntInfoVO updateDisAgrInfo(DisAgrEntryForm disAgrEntryForm, String opeId) throws Exception, SQLException {
        if(logger.isDebugEnabled()) {
            logger.methodLog("updateDisAgrInfo", new Object[]{disAgrEntryForm, opeId}, Constant.METHOD_START);
        }

        // DB�R�l�N�V�����p
        Connection conn = null;
        // SQL�����s�p
        PreparedStatement pstmt = null;
        // ���sSQL��
        StringBuffer strSql = new StringBuffer();

        try {
            conn = getConnection();

            /** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD Begin */
            //strSql.append("update ").append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_EXT set ");
            strSql.append("update ").append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_DT set ");
            /** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD End */
            strSql.append("  ACT_SUBJECT = ?");                                             // ��������
            strSql.append("  ,TEL_NG = ?");                                                 // �A�������Ȃ��E�ʒk���ł��Ȃ�
            strSql.append("  ,CNFDNC_NG = ?");                                              // ���̑�
            strSql.append("  ,FRM_ACQUAINTANCE_APPS = ?");                                  // ����W�l�������͒ʔ̂��_��
            strSql.append("  ,UNDERTAKE_NG = ?");                                           // ���󂪕s�\�ł���
            strSql.append("  ,MEMO = ?");                                                   // ���̑�����
            strSql.append("  ,COMPE_ATER_CMPNY1 = ?");                                      // ��������1
            strSql.append("  ,COMPE_ATER_CMPNY1_PRDCT = ?");                                // ��������1���i
            strSql.append("  ,COMPE_ATER_CMPNY2 = ?");                                      // ��������2
            strSql.append("  ,COMPE_ATER_CMPNY2_PRDCT = ?");                                // ��������2���i
            strSql.append("  ,COMPE_ATER_CMPNY3 = ?");                                      // ��������3
            strSql.append("  ,COMPE_ATER_CMPNY3_PRDCT = ?");                                // ��������3���i
            strSql.append("  ,UPDATE_PG_ID = 'CUSEXT'");                                    // �X�V�v���O����ID
            strSql.append("  ,UPDATE_DT = SYSDATE");                                        // �X�V����
            strSql.append("  ,UPDATE_BY = ? ");                                             // �X�V��ID
            strSql.append(" where ");
            strSql.append("  AGRMNT_NO = ?");         // �s����No

            // ���s����SQL�������O�ɏo��
            logger.printSql( strSql.toString() );

            // SQL���ݒ�
            pstmt = conn.prepareStatement(strSql.toString());
            pstmt.setString(1, disAgrEntryForm.getActSubject());
            pstmt.setString(2, disAgrEntryForm.getTelNg());
            pstmt.setString(3, disAgrEntryForm.getCnfdncNg());
            pstmt.setString(4, disAgrEntryForm.getFrmAcquaintanceApps());
            pstmt.setString(5, disAgrEntryForm.getUndertakeNg());
            pstmt.setString(6, disAgrEntryForm.getMemo());
            pstmt.setString(7, disAgrEntryForm.getCompeAterCmpny1());
            pstmt.setString(8, disAgrEntryForm.getCompeAterCmpny1Prdct());
            pstmt.setString(9, disAgrEntryForm.getCompeAterCmpny2());
            pstmt.setString(10, disAgrEntryForm.getCompeAterCmpny2Prdct());
            pstmt.setString(11, disAgrEntryForm.getCompeAterCmpny3());
            pstmt.setString(12, disAgrEntryForm.getCompeAterCmpny3Prdct());
            pstmt.setString(13, opeId);
            pstmt.setString(14, disAgrEntryForm.getDisAgrmntNo());

            // �s������X�V
            pstmt.executeUpdate();

        } catch( SQLException sqle ) {
            logger.error("E0002" , sqle );
            throw sqle;
        } catch(Exception e) {
            logger.error("E0003" ,e );
            throw e;
        } finally {
            try {
                close( conn, pstmt, null);
            } finally {
                pstmt = null;
                conn = null;
            }
        }
        if(logger.isDebugEnabled()) {
            logger.methodLog("updateDisAgrInfo", Constant.METHOD_END);
        }
        return getDisAgrDetailInfo(disAgrEntryForm.getDisAgrmntNo());
    }

    /**
     * �s��������폜����B
     * @param disAgrId �폜����s�������ID
     * @throws Exception ��O����
     * @throws SQLException ��O����
     */
    public void deleteDisAgrInfo(String disAgrId) throws Exception, SQLException {
        if(logger.isDebugEnabled()) {
            logger.methodLog("deleteDisAgrInfo", new Object[]{disAgrId}, Constant.METHOD_START);
        }

        // DB�R�l�N�V�����p
        Connection conn = null;
        // SQL�����s�p
        PreparedStatement pstmt = null;
        // ���sSQL��
        StringBuffer strSql = new StringBuffer();

        try {
            conn = getConnection();

            /** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD Begin */
            //strSql.append("delete from ").append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_EXT where AGRMNT_NO = ?");
            strSql.append("delete from ").append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_DT where AGRMNT_NO = ?");
            /** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD End */
            // ���s����SQL�������O�ɏo��
            logger.printSql( strSql.toString() );

            // SQL���ݒ�
            pstmt = conn.prepareStatement(strSql.toString());
            pstmt.setString(1, disAgrId);

            // �s������폜
            pstmt.executeUpdate();

        } catch( SQLException sqle ) {
            logger.error("E0002" , sqle );
            throw sqle;
        } catch(Exception e) {
            logger.error("E0003" ,e );
            throw e;
        } finally {
            try {
                close( conn, pstmt, null);
            } finally {
                pstmt = null;
                conn = null;
            }
        }
        if(logger.isDebugEnabled()) {
            logger.methodLog("deleteDisAgrInfo", Constant.METHOD_END);
        }
    }

    /**
     * �n���ꂽ�������ID�Ɋ֘A����s��������폜����B
     * @param actId �폜����s������̊���ID
     * @param conn �R�l�N�V����
     * @throws Exception ��O����
     * @throws SQLException ��O����
     */
    public void deleteAllDisAgrInfo(String actId, Connection conn) throws Exception, SQLException {
        if(logger.isDebugEnabled()) {
            logger.methodLog("deleteAllDisAgrInfo", new Object[]{actId}, Constant.METHOD_START);
        }

        // SQL�����s�p
        PreparedStatement pstmt = null;
        // ���sSQL��
        StringBuffer strSql = new StringBuffer();

        try {


        	/** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD Begin */
            //strSql.append("delete from ").append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_EXT where ACT_SFDC_ID = ?");
        	strSql.append("delete from ").append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_DT where ACT_SFDC_ID = ?");
            /** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD Begin */
            // ���s����SQL�������O�ɏo��
            logger.printSql( strSql.toString() );

            // SQL���ݒ�
            pstmt = conn.prepareStatement(strSql.toString());
            pstmt.setString(1, actId);

            // �s������폜
            pstmt.executeUpdate();

        } catch( SQLException sqle ) {
            logger.error("E0002" , sqle );
            throw sqle;
        } catch(Exception e) {
            logger.error("E0003" ,e );
            throw e;
        } finally {
            try {
                close( null, pstmt, null);
            } finally {
                pstmt = null;
                conn = null;
            }
        }
        if(logger.isDebugEnabled()) {
            logger.methodLog("deleteAllDisAgrInfo", Constant.METHOD_END);
        }
    }

    /**
     * �s��������擾����SQL�����쐬�B
     * @param isDetail �s������ڍׂ̎擾�L���Btrue-�s������ڍׁAfalse-�s�����񃊃X�g
     * @param isBranch true - �x�Ѓ}Leads�Afalse - AG�{��Leads
     * @return String �擾�pSQL
     */
    private String getDisAgrInfoSql(boolean isDetail, boolean isBranch) {
        StringBuffer sqlSb = new StringBuffer();
        sqlSb.append("select ");
        sqlSb.append("  AGRMNT_NO ");                                                           // �s����No
        sqlSb.append("  ,COMPE_ATER_CMPNY1 ");                                                  // ��������1
        sqlSb.append("  ,COMPE_ATER_CMPNY1_PRDCT ");                                            // ��������1���i
        sqlSb.append("  ,MEMO ");                                                               // ���̑�����
        if(isDetail) {
            sqlSb.append("  ,TO_CHAR(AGR.ENTRY_DT, 'YYYY/MM/DD HH24:MI') AS ENTRY_DT ");        // �o�^����
            sqlSb.append("  ,TO_CHAR(AGR.UPDATE_DT, 'YYYY/MM/DD HH24:MI') AS UPDATE_DT ");      // �X�V����
            sqlSb.append("  ,ACT.ACT_SUBJECT ");                                                // ��������
            sqlSb.append("  ,AGR.HOUSEHOLD_ID ");                                               // �֘A�ڋq�h�c
            sqlSb.append("  ,TEL_NG ");                                                         // �A�������Ȃ��E�ʒk���ł��Ȃ�
            sqlSb.append("  ,CNFDNC_NG ");                                                      // ���̑�
            sqlSb.append("  ,FRM_ACQUAINTANCE_APPS ");                                          // ����W�l�������͒ʔ̂��_��
            sqlSb.append("  ,UNDERTAKE_NG ");                                                   // ���󂪕s�\�ł���
            sqlSb.append("  ,COMPE_ATER_CMPNY2 ");                                              // ��������2
            sqlSb.append("  ,COMPE_ATER_CMPNY2_PRDCT ");                                        // ��������2���i
            sqlSb.append("  ,COMPE_ATER_CMPNY3 ");                                              // ��������3
            sqlSb.append("  ,COMPE_ATER_CMPNY3_PRDCT ");                                        // ��������3���i
            sqlSb.append("  ,AGR.ENTRY_PG_ID ");                                                // �o�^�v���O����ID
            sqlSb.append("  ,AGR.ENTRY_BY ");                                                   // �o�^��ID
            sqlSb.append("  ,AGR.UPDATE_PG_ID ");                                               // �X�V�v���O����ID
            sqlSb.append("  ,AGR.UPDATE_BY ");                                                  // �X�V��ID
        } else {
            sqlSb.append("  ,TO_CHAR(AGR.ENTRY_DT, 'YYYY/MM/DD') AS ENTRY_DT ");                // �o�^����
            sqlSb.append("  ,TO_CHAR(AGR.UPDATE_DT, 'YYYY/MM/DD') AS UPDATE_DT ");              // �X�V����
        }
        sqlSb.append("from ");

        /** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD Begin */
//        if(isBranch) {
//            sqlSb.append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_EXT AGR,");
//            sqlSb.append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_ACT_DT_EXT ACT ");
//        } else {
//            sqlSb.append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_DT AGR,");
//            sqlSb.append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_ACT_DT ACT ");
//        }
        sqlSb.append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_LDS_AGRMNT_INFO_DT AGR,");
        sqlSb.append(Constant.TABLE_SCHEMA).append(".").append("T_MST_CUS_ACT_DT ACT ");
        /** 2016/05/18 ���@�� ADA IA���C���Ή�Phase2�@1.1 CUSEXT�ł̃f�[�^�o�^��ύX ADD Begin */
        sqlSb.append("where ");
        sqlSb.append("  ACT.SFDC_ID = AGR.ACT_SFDC_ID");
        if(isBranch) {
            sqlSb.append("  AND ACT.REGIST_KBN = '1'");
        }
        else {
            sqlSb.append("  AND ACT.REGIST_KBN = '0'");
        }
        if(isDetail) {
            // ����No�Ő���ڍ׎擾
            sqlSb.append("  AND AGRMNT_NO = ?");
        } else {
            // �������L�[�ƕR�t�����擾
            sqlSb.append("  AND ACT_SFDC_ID = ?");
            sqlSb.append("  AND AGR.AGRMNT_NAGRMNT = '�s����'");
        }
        sqlSb.append(" order by AGR.ENTRY_DT");

        return sqlSb.toString();
    }
}
