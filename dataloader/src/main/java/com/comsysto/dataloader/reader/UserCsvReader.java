package com.comsysto.dataloader.reader;

import com.comsysto.shop.repository.user.model.User;
import org.springframework.stereotype.Component;

/**
 * @author zutherb
 */
@Component("userCsvReader")
public class UserCsvReader extends AbstractCsvReader<User> {

    private String filePath = "com/comsysto/dataloader/user.csv";

    @Override
    protected String getClassPathFilePath() {
        return filePath;
    }

    @Override
    protected String[] getColumnMapping() {
        return new String[]{"salutationType", "firstname", "lastname", "email","categories"};
    }

    @Override
    protected Class<User> getDestinationClass() {
        return User.class;
    }

    /**
     * Setter for property override configure
     * @param filePath filePath of the csv file that should be loaded
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
