package vn.edu.ute.productmgmt.controller;

import vn.edu.ute.productmgmt.model.dto.AccountDTO;
import vn.edu.ute.productmgmt.model.dto.ClassSectionDTO;
import vn.edu.ute.productmgmt.model.util.SessionManager;
import vn.edu.ute.productmgmt.service.ClassSectionService;
import vn.edu.ute.productmgmt.view.LecturerClassPanel;
import java.util.List;

public class LecturerClassController {
    private LecturerClassPanel view;
    private ClassSectionService classService;

    public LecturerClassController(LecturerClassPanel view) {
        this.view = view;
        this.classService = new ClassSectionService();

        initEvents();
        loadLecturerClasses();
    }

    private void initEvents() {
        view.getBtnRefresh().addActionListener(e -> loadLecturerClasses());
    }

    public void loadLecturerClasses() {
        AccountDTO currentAcc = SessionManager.getCurrentAccount();
        // Giờ đây getLecturerId() đã tồn tại trong AccountDTO
        if (currentAcc == null || currentAcc.getLecturerId() == null) {
            return;
        }

        List<ClassSectionDTO> list = classService.getClassesByLecturer(currentAcc.getLecturerId());
        view.getTableModel().setRowCount(0);
        for (ClassSectionDTO cs : list) {
            view.getTableModel().addRow(new Object[]{
                    cs.getId(),
                    cs.getClassCode(),
                    cs.getCourseName(),
                    cs.getRoom(),
                    cs.getScheduleDisplay(),
                    cs.getCapacityDisplay()
            });
        }
    }
}