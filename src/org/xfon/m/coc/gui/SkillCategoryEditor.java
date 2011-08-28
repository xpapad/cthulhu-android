package org.xfon.m.coc.gui;

import java.util.List;

import org.xfon.m.coc.ISkill;
import org.xfon.m.coc.R;
import org.xfon.m.coc.Skill;
import org.xfon.m.coc.SkillCategory;
import org.xfon.m.coc.Skills;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class SkillCategoryEditor extends BaseSkillEditor implements OnClickListener {
	private Button btnAddSkill;
	private SkillCategory category;
	
	public SkillCategoryEditor(Context context) {
		super(context, -1, null, null);
	}
	
	public SkillCategoryEditor(Context context, Skills skills, SkillCategory category ) {
		super(context, R.layout.skill_category_editor, skills, category);
		this.category = category;
        setName( category.getName() );        
        btnAddSkill = (Button)findViewById( R.id.btnAddSkill );
       	btnAddSkill.setOnClickListener( this );       	       
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();		
		for ( ISkill skill: mSkills.list() ) {
			if ( skill.isCategory() ) continue;
			Skill sk = (Skill)skill;
       		if ( sk.isAdded() && sk.getCategory() == (SkillCategory)mSkill ) {
       			addNewSkill( sk, true );
       		}
       	}
	}
	
	private void setName( String title ) {
		TextView tv = (TextView)this.findViewById( R.id.name );		
		tv.setText( title );
	}
	
	/**
	 * Add a new skill in this category
	 */
	@Override
	public void onClick(View v) {
		if ( v != btnAddSkill ) return;				
		addNewSkill( mSkills.newSkill( (SkillCategory)mSkill ), false );
	}
	
	private void addNewSkill( Skill newSkill, boolean lock ) {
		int index = getIndexInParent();
		EditableSkillEditor editor = new EditableSkillEditor( getContext(), mSkills, newSkill );		
		TableLayout table = (TableLayout)this.getParent();
		table.addView( editor, index + 1 );
		editor.addOnSkillChangedListeners( getOnSkillChangedListeners() );		
		editor.setEditFocus();
		if ( lock ) editor.lock();
	}	
	
	private int getIndexInParent() {		
		ViewGroup parent = (ViewGroup)this.getParent();
		int count = parent.getChildCount();
		for ( int i = 0; i < count; i++ ) {
			View child = parent.getChildAt( i );
			if ( child == (View)this ) {				
				return i;
			}
		}
		return -1;
	}
	
 
	
}
