package ru.ixxo.crux.client.springlayout;

import javax.swing.JComponent;
import javax.swing.SpringLayout;

public class SpringOrder {

	SpringLayout layout;

	/*
	 * Order position types
	 */

	public final static String ORDER_ON_THE_RIGHT = "On The Right";

	public final static String ORDER_BENEATH = "Beneath";

	/*
	 * Arrange types
	 */

	public final static String NO_ARRANGE = "No Arrange";

	public final static String ARRANGE_BY_FIRST_EDGE = "Arrange By First Edge";

	public final static String ARRANGE_BY_SECOND_EDGE = "Arrange By Second Edge";

	public final static String ARRANGE_BY_BOTH_EDGES = "Arrange By Both Edges";

	/*
	 * Current arrange type
	 */

	protected String arrangeType = NO_ARRANGE;

	public SpringOrder(SpringLayout layout) {
		this.layout = layout;
	}

	public String getArrangeType() {
		return arrangeType;
	}

	public void setArrangeType(String arrangeType) {
		this.arrangeType = arrangeType;
	}

	public void order(JComponent target, JComponent source, String orderType,
			String arrangeType) {
		if (orderType == null || target == null || source == null) {
			throw new RuntimeException("One of parameter isn't specified");
		}

		if (arrangeType != null) {
			/**
			 * Change current arrange type
			 */
			setArrangeType(arrangeType);
		}

		if (ORDER_BENEATH.equals(orderType)) {
			orderBeneath(target, source);
		}

		if (ORDER_ON_THE_RIGHT.equals(orderType)) {
			orderOnRight(target, source);
		}

		arrange(target, source, orderType, arrangeType);
	}

	protected void orderBeneath(JComponent target, JComponent source) {
		layout.putConstraint(SpringLayout.NORTH, target, 0, SpringLayout.SOUTH,
				source);
	}

	protected void orderOnRight(JComponent target, JComponent source) {
		layout.putConstraint(SpringLayout.WEST, target, 0, SpringLayout.EAST,
				source);
	}

	public void arrange(JComponent target, JComponent source, String orderType,
			String arrangeType) {
		if (arrangeType != null) {
			/**
			 * Change current arrange type
			 */
			setArrangeType(arrangeType);
		}

		if (ARRANGE_BY_FIRST_EDGE.equalsIgnoreCase(arrangeType)) {
			if (ORDER_BENEATH.equalsIgnoreCase(orderType)) {
				arrangeByLeftEdge(target, source);
			}

			if (ORDER_ON_THE_RIGHT.equalsIgnoreCase(orderType)) {
				arrangeByNorthEdge(target, source);
			}
		}

		if (ARRANGE_BY_SECOND_EDGE.equalsIgnoreCase(arrangeType)) {
			if (ORDER_BENEATH.equalsIgnoreCase(orderType)) {
				arrangeByRightEdge(target, source);
			}

			if (ORDER_ON_THE_RIGHT.equalsIgnoreCase(orderType)) {
				arrangeBySouthEdge(target, source);
			}
		}

		if (ARRANGE_BY_BOTH_EDGES.equalsIgnoreCase(arrangeType)) {
			if (ORDER_BENEATH.equalsIgnoreCase(orderType)) {
				arrangeByHorizontal(target, source);
			}

			if (ORDER_ON_THE_RIGHT.equalsIgnoreCase(orderType)) {
				arrangeByVertical(target, source);
			}
		}

		if (NO_ARRANGE.equalsIgnoreCase(arrangeType)) {

		}
	}

	protected void arrangeByHorizontal(JComponent target, JComponent source) {
		arrangeByRightEdge(target, source);
		arrangeByLeftEdge(target, source);
	}

	protected void arrangeByVertical(JComponent target, JComponent source){
		arrangeBySouthEdge(target, source);
		arrangeByNorthEdge(target, source);
	}
	
	protected void arrangeByLeftEdge(JComponent target, JComponent source) {

		layout.putConstraint(SpringLayout.WEST, target, 0, SpringLayout.WEST,
				source);
	}

	protected void arrangeByNorthEdge(JComponent target, JComponent source) {
		layout.putConstraint(SpringLayout.NORTH, target, 0, SpringLayout.NORTH,
				source);
	}

	protected void arrangeByRightEdge(JComponent target, JComponent source) {
		layout.putConstraint(SpringLayout.EAST, target, 0, SpringLayout.EAST,
				source);
	}

	protected void arrangeBySouthEdge(JComponent target, JComponent source) {
		layout.putConstraint(SpringLayout.SOUTH, target, 0, SpringLayout.SOUTH,
				source);
	}

}
