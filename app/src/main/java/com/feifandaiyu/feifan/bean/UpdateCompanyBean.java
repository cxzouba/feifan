package com.feifandaiyu.feifan.bean;

import java.util.List;

/**
 * Created by houdaichang on 2017/8/31.
 */

public class UpdateCompanyBean {

    /**
     * code : 1
     * show : {"picarr":["https://img.hrbffdy.com/5cc9d4ec-853e-4ea4-bac9-0f93c784f6f34105520170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:uOsxl_YyU6qjDNvePU6v1eGPz04="],"reportPic":["https://img.hrbffdy.com/9d438c1d-96bd-45a6-a104-5ff34cb7c83c2019020170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:D6FzM57MRoDcDggXzzX4q6eirWk="],"cardPic":["https://img.hrbffdy.com/d7b605c3-45c6-4c09-a1e6-9dcadc80b9e03454420170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:FEh9I_i_Eiva1fhvi2_6-PmfhmU="],"recordingPic":["https://img.hrbffdy.com/761d1502-8ef4-4b09-893d-9283363cb6589872720170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:klfzhBa8dz-P2ww-Un7wjzseWhE="],"bankflowPic":["https://img.hrbffdy.com/659bec97-4900-4a03-a69b-f959877895434372920170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:M3zcZagMGa2gqNAfmONpSazrP-4="],"financialPic":["https://img.hrbffdy.com/848161e3-4c23-4f9d-a035-f76d21708cc93405420170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:gPgjQXBMCyXR76ul7f52hZsi_L0="],"propertyPic":["https://img.hrbffdy.com/4c8af6f8-56d7-4589-82a6-3f36c69eb9343650820170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:ERyiJ7sBPO076xjgNF5sQ5xsFJk="],"creditReporting":["https://img.hrbffdy.com/34159efa-0601-4cca-adda-f353bacfcdd44840120170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:0uuxI2lqhQCk1vSCAaLXMcW58QA="],"visitsPic":["https://img.hrbffdy.com/2cf044b5-b80c-4f54-8631-d68fd20ab2e41440420170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:ru7ZLnSqjssbCeDmMILCgcOSvW0="],"businessPic":["https://img.hrbffdy.com/f9bf9a77-abda-406d-9cb1-6885aa1431864441920170814.png?e=1503548748&token=1LK9GzrGIAsJDZvNQr-_hwd_GCyTR1sdp1SSX20d:B8LwEWzXD45h5whlsjgrhI3EMdQ="]}
     * list : {"picarr":["5cc9d4ec-853e-4ea4-bac9-0f93c784f6f34105520170814.png"],"reportPic":["9d438c1d-96bd-45a6-a104-5ff34cb7c83c2019020170814.png"],"cardPic":["d7b605c3-45c6-4c09-a1e6-9dcadc80b9e03454420170814.png"],"recordingPic":["761d1502-8ef4-4b09-893d-9283363cb6589872720170814.png"],"bankflowPic":["659bec97-4900-4a03-a69b-f959877895434372920170814.png"],"financialPic":["848161e3-4c23-4f9d-a035-f76d21708cc93405420170814.png"],"propertyPic":["4c8af6f8-56d7-4589-82a6-3f36c69eb9343650820170814.png"],"creditReporting":["34159efa-0601-4cca-adda-f353bacfcdd44840120170814.png"],"visitsPic":["2cf044b5-b80c-4f54-8631-d68fd20ab2e41440420170814.png"],"businessPic":["f9bf9a77-abda-406d-9cb1-6885aa1431864441920170814.png"]}
     */

    private int code;
    private ShowBean show;
    private ListBean list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ShowBean getShow() {
        return show;
    }

    public void setShow(ShowBean show) {
        this.show = show;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ShowBean {
        private List<String> picarr;
        private List<String> reportPic;
        private List<String> cardPic;
        private List<String> recordingPic;
        private List<String> bankflowPic;
        private List<String> financialPic;
        private List<String> propertyPic;
        private List<String> creditReporting;
        private List<String> visitsPic;
        private List<String> businessPic;

        public List<String> getPicarr() {
            return picarr;
        }

        public void setPicarr(List<String> picarr) {
            this.picarr = picarr;
        }

        public List<String> getReportPic() {
            return reportPic;
        }

        public void setReportPic(List<String> reportPic) {
            this.reportPic = reportPic;
        }

        public List<String> getCardPic() {
            return cardPic;
        }

        public void setCardPic(List<String> cardPic) {
            this.cardPic = cardPic;
        }

        public List<String> getRecordingPic() {
            return recordingPic;
        }

        public void setRecordingPic(List<String> recordingPic) {
            this.recordingPic = recordingPic;
        }

        public List<String> getBankflowPic() {
            return bankflowPic;
        }

        public void setBankflowPic(List<String> bankflowPic) {
            this.bankflowPic = bankflowPic;
        }

        public List<String> getFinancialPic() {
            return financialPic;
        }

        public void setFinancialPic(List<String> financialPic) {
            this.financialPic = financialPic;
        }

        public List<String> getPropertyPic() {
            return propertyPic;
        }

        public void setPropertyPic(List<String> propertyPic) {
            this.propertyPic = propertyPic;
        }

        public List<String> getCreditReporting() {
            return creditReporting;
        }

        public void setCreditReporting(List<String> creditReporting) {
            this.creditReporting = creditReporting;
        }

        public List<String> getVisitsPic() {
            return visitsPic;
        }

        public void setVisitsPic(List<String> visitsPic) {
            this.visitsPic = visitsPic;
        }

        public List<String> getBusinessPic() {
            return businessPic;
        }

        public void setBusinessPic(List<String> businessPic) {
            this.businessPic = businessPic;
        }
    }

    public static class ListBean {
        private List<String> picarr;
        private List<String> reportPic;
        private List<String> cardPic;
        private List<String> recordingPic;
        private List<String> bankflowPic;
        private List<String> financialPic;
        private List<String> propertyPic;
        private List<String> creditReporting;
        private List<String> visitsPic;
        private List<String> businessPic;

        public List<String> getPicarr() {
            return picarr;
        }

        public void setPicarr(List<String> picarr) {
            this.picarr = picarr;
        }

        public List<String> getReportPic() {
            return reportPic;
        }

        public void setReportPic(List<String> reportPic) {
            this.reportPic = reportPic;
        }

        public List<String> getCardPic() {
            return cardPic;
        }

        public void setCardPic(List<String> cardPic) {
            this.cardPic = cardPic;
        }

        public List<String> getRecordingPic() {
            return recordingPic;
        }

        public void setRecordingPic(List<String> recordingPic) {
            this.recordingPic = recordingPic;
        }

        public List<String> getBankflowPic() {
            return bankflowPic;
        }

        public void setBankflowPic(List<String> bankflowPic) {
            this.bankflowPic = bankflowPic;
        }

        public List<String> getFinancialPic() {
            return financialPic;
        }

        public void setFinancialPic(List<String> financialPic) {
            this.financialPic = financialPic;
        }

        public List<String> getPropertyPic() {
            return propertyPic;
        }

        public void setPropertyPic(List<String> propertyPic) {
            this.propertyPic = propertyPic;
        }

        public List<String> getCreditReporting() {
            return creditReporting;
        }

        public void setCreditReporting(List<String> creditReporting) {
            this.creditReporting = creditReporting;
        }

        public List<String> getVisitsPic() {
            return visitsPic;
        }

        public void setVisitsPic(List<String> visitsPic) {
            this.visitsPic = visitsPic;
        }

        public List<String> getBusinessPic() {
            return businessPic;
        }

        public void setBusinessPic(List<String> businessPic) {
            this.businessPic = businessPic;
        }
    }
}
