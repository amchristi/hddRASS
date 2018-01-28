package PostProcessing;

import ASTManipulation.ExtendedStatement;
import japa.parser.ast.stmt.Statement;

import java.util.List;

/**
 * Created by root on 1/8/17.
 */
public class StatInfo {

    public int numberOfNodesReduced;
    public int highestlevelFromLeafNode;
    List<Statement> statementList;
    List<ExtendedStatement> extendedStatementList;

    public StatInfo(){
        numberOfNodesReduced = 0;
        highestlevelFromLeafNode = 0;

    }

}
