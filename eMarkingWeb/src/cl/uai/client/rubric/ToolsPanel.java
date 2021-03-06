/**
 * 
 */
package cl.uai.client.rubric;

import cl.uai.client.MarkingInterface;

import cl.uai.client.resources.Resources;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * @author Jorge
 *
 */
public class ToolsPanel extends Composite {
	
	/** Panel holding tools **/
	private TabPanel toolsPanel = null;
	/** Previous comments interface **/
	private PreviousCommentsInterface previousComments = null;
	/** General feedback **/
	private GeneralFeedbackInterface generalFeedback = null;
	/** Sorting pages interfaces **/
	private SortPagesInterface sortPages = null;
	
	/** Chat interface (just for collaborative features) **/
	private ChatInterface chat = null;
	/** Wall interface (just for collaborative features) **/
	private WallInterface wall = null;
	
	private MarksSummaryInterface marksSummary = null;
	
	/** Scroll panel for managing a large number of comments **/
	private ScrollPanel scroll;

	/**
	 * @return the generalFeedback
	 */
	public GeneralFeedbackInterface getGeneralFeedback() {
		return generalFeedback;
	}

	/**
	 * @return the previousComments
	 */
	public PreviousCommentsInterface getPreviousComments() {
		return previousComments;
	}

	public ToolsPanel() {
		toolsPanel = new TabPanel();
		toolsPanel.addStyleName(Resources.INSTANCE.css().previouscomments());
		
		//Collaborative features
		if(MarkingInterface.getCollaborativeFeatures()==1){
			//Setting height
			int height = Window.getClientHeight()*50/100;
			//Chat
			chat = new ChatInterface();
			toolsPanel.add(chat, "Chat");
			chat.setHeight(height+"px");
			//Wall
			wall = new WallInterface();
			toolsPanel.add(wall, "Muro");
			wall.setHeight(height+"px");
		}
		
		// Marking tools
		generalFeedback = new GeneralFeedbackInterface();

		previousComments = new PreviousCommentsInterface();
		previousComments.setVisible(!MarkingInterface.readonly);

		if(!MarkingInterface.readonly) {
			toolsPanel.add(previousComments, MarkingInterface.messages.PreviousComments());
			toolsPanel.add(generalFeedback, MarkingInterface.messages.GeneralFeedback());
		}

		// Sorting pages
		if(MarkingInterface.supervisor) {
			sortPages = new SortPagesInterface();
			toolsPanel.add(sortPages, MarkingInterface.messages.SortPages());
		}

		marksSummary = new MarksSummaryInterface();
		toolsPanel.add(marksSummary, MarkingInterface.messages.Score());
		
		toolsPanel.selectTab(0);
		
		scroll = new ScrollPanel();
		scroll.add(toolsPanel);

		this.initWidget(scroll);
	}
	
	public void loadSumissionData() {
		marksSummary.loadSubmissionData();
	}
	
	@Override
	protected void onLoad() {
		super.onLoad();
		
		marksSummary.loadSubmissionData();
	}
	
	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		 scroll.setHeight(height);
	}
}
