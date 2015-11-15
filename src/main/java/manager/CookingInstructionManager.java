package manager;

import factory.database.ConnectionFactory;
import model.CookingInstruction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by wendywang on 2015-11-14.
 */
public class CookingInstructionManager

{
        public ArrayList<CookingInstruction> getCookingInstructions(Connection con) throws SQLException
        {
            ArrayList<CookingInstruction> cookingInstructions = new ArrayList<CookingInstruction>();
            try (Connection connection = ConnectionFactory.getConnection())
            {
                PreparedStatement stmt = con.prepareStatement("SELECT * FROM cooking_instruction");
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    CookingInstruction cookingInstructionObj = new CookingInstruction();
                    cookingInstructionObj.setInstruction(rs.getString("instruction"));
                    cookingInstructionObj.setInstructionID(rs.getInt("instruction_id"));
                    cookingInstructionObj.setRecipeID(rs.getInt("recipe_id"));
                    cookingInstructionObj.setImageURL((rs.getString("image_url")));
                    cookingInstructions.add(cookingInstructionObj);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return cookingInstructions;
        }
}
