package Altra.ModJam.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelDwarf extends ModelBase
{
  //fields
    ModelRenderer beard1;
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer arm2;
    ModelRenderer arm1;
    ModelRenderer beard2;
    ModelRenderer beard3;
    ModelRenderer beard5;
    ModelRenderer beard4;
    ModelRenderer beard6;
    ModelRenderer beard7;
  
  public ModelDwarf(float f)
  {
    textureWidth = 64;
    textureHeight = 32;
    
      beard1 = new ModelRenderer(this, 0, 0);
      beard1.addBox(-3F, 0F, 0F, 5, 2, 1);
      beard1.setRotationPoint(0F, 8F, -9F);
      beard1.setTextureSize(64, 32);
      beard1.mirror = true;
      setRotation(beard1, 0F, 0F, 0F);
      head = new ModelRenderer(this, 34, 22);
      head.addBox(-4F, -4F, -3F, 5, 5, 5);
      head.setRotationPoint(1F, 9F, -5F);
      head.setTextureSize(64, 32);
      head.mirror = true;
      setRotation(head, 0F, 0F, 0F);
      body = new ModelRenderer(this, 14, 7);
      body.addBox(-5F, 0F, -6F, 9, 6, 9);
      body.setRotationPoint(0F, 13F, -9F);
      body.setTextureSize(64, 32);
      body.mirror = true;
      setRotation(body, 1.570796F, 0F, 0F);
      leg1 = new ModelRenderer(this, 0, 23);
      leg1.addBox(-2F, 0F, -3F, 3, 5, 4);
      leg1.setRotationPoint(-3F, 19F, -5F);
      leg1.setTextureSize(64, 32);
      leg1.mirror = true;
      setRotation(leg1, 0F, 0F, 0F);
      leg2 = new ModelRenderer(this, 0, 14);
      leg2.addBox(-2F, 0F, -3F, 3, 5, 4);
      leg2.setRotationPoint(3F, 19F, -5F);
      leg2.setTextureSize(64, 32);
      leg2.mirror = true;
      setRotation(leg2, 0F, 0F, 0F);
      arm2 = new ModelRenderer(this, 24, 22);
      arm2.addBox(0F, 0F, -2F, 2, 7, 3);
      arm2.setRotationPoint(4F, 11F, -5F);
      arm2.setTextureSize(64, 32);
      arm2.mirror = true;
      setRotation(arm2, 0F, 0F, 0F);
      arm1 = new ModelRenderer(this, 14, 22);
      arm1.addBox(-2F, 0F, -1F, 2, 7, 3);
      arm1.setRotationPoint(-5F, 11F, -6F);
      arm1.setTextureSize(64, 32);
      arm1.mirror = true;
      setRotation(arm1, 0F, 0F, 0F);
      beard2 = new ModelRenderer(this, 20, 1);
      beard2.addBox(-3F, 0F, 0F, 1, 2, 4);
      beard2.setRotationPoint(5F, 8F, -8F);
      beard2.setTextureSize(64, 32);
      beard2.mirror = true;
      setRotation(beard2, 0F, 0F, 0F);
      beard3 = new ModelRenderer(this, 11, 0);
      beard3.addBox(0F, 0F, -1F, 1, 2, 4);
      beard3.setRotationPoint(-4F, 8F, -7F);
      beard3.setTextureSize(64, 32);
      beard3.mirror = true;
      setRotation(beard3, 0F, 0F, 0F);
      beard5 = new ModelRenderer(this, 0, 0);
      beard5.addBox(-2F, -1F, -1F, 3, 1, 1);
      beard5.setRotationPoint(0F, 15F, -9F);
      beard5.setTextureSize(64, 32);
      beard5.mirror = true;
      setRotation(beard5, 0F, 0F, 0F);
      beard4 = new ModelRenderer(this, 0, 7);
      beard4.addBox(-3F, -1F, -1F, 5, 5, 2);
      beard4.setRotationPoint(0F, 10F, -9.2F);
      beard4.setTextureSize(64, 32);
      beard4.mirror = true;
      setRotation(beard4, 0F, 0F, 0F);
      beard6 = new ModelRenderer(this, 0, 0);
      beard6.addBox(-1F, 0F, -1F, 1, 3, 1);
      beard6.setRotationPoint(-3F, 10F, -9F);
      beard6.setTextureSize(64, 32);
      beard6.mirror = true;
      setRotation(beard6, 0F, 0F, 0F);
      beard7 = new ModelRenderer(this, 0, 0);
      beard7.addBox(-1F, 0F, -1F, 1, 3, 1);
      beard7.setRotationPoint(3F, 10F, -9F);
      beard7.setTextureSize(64, 32);
      beard7.mirror = true;
      setRotation(beard7, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    beard1.render(f5);
    head.render(f5);
    body.render(f5);
    leg1.render(f5);
    leg2.render(f5);
    arm2.render(f5);
    arm1.render(f5);
    beard2.render(f5);
    beard3.render(f5);
    beard5.render(f5);
    beard4.render(f5);
    beard6.render(f5);
    beard7.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  /**
   * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
   * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
   * "far" arms and legs can swing at most.
   */
  public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity){
	  this.arm1.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.7F * par2 * 0.5F;
	  this.arm2.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.7F * par2 * 0.5F;
      this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.7F * par2 * 0.5F;
      this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.7F * par2 * 0.5F;
  }

}
