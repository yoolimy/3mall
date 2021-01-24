package admin.svc;

import static db.JdbcUtil.*;
import java.util.*;
import java.sql.*;
import admin.dao.*;
import vo.*;

public class SettingFormSvc {
	public boolean settingForm(SettingInfo set) {
		boolean isSuccess = false;
		Connection conn = getConnection();
		SetDao setDao = SetDao.getInstance();
		setDao.setConnection(conn);
		int result = setDao.settingForm(set);
		if (result > 0) {
			isSuccess = true;
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		return isSuccess;
	}
}
